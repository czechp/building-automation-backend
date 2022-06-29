package app.web.pczportfolio.pczbuildingautomation.account.domain;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountEntity;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountCreateCmdDto;
import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiledException;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PUBLIC, setterPrefix = "with")
@EqualsAndHashCode
@ToString
public class Account {
    private long id;

    private long version;

    private LocalDateTime creationTimestamp;
    private String username;
    private String password;
    private String email;

    private AccountRole accountRole;
    private AccountConfiguration accountConfiguration;

    Account(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.accountRole = AccountRole.USER;
        this.accountConfiguration = new AccountConfiguration();
    }


    public static Account create(AccountCreateCmdDto dto, Function<String, String> hashPasswordFunction) {
        comparePasswords(dto.getPassword(), dto.getPasswordConfirm());
        Account newAccount = new Account(dto.getUsername(), dto.getPassword(), dto.getEmail());
        newAccount.setPassword(hashPasswordFunction.apply(dto.getPassword()));
        newAccount.creationTimestamp = LocalDateTime.now();
        return newAccount;
    }
    
    private static void comparePasswords(String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm))
            throw new ConditionsNotFulFiledException("Passwords are not equal");
    }

    public void adminActivate() {
        this.accountConfiguration.setAdminActivation(true);
    }

    public void adminDeactivate() {
        this.accountConfiguration.setAdminActivation(false);
    }

    public void confirmEmail(String token) {
        if (this.accountConfiguration.getEnableToken().equals(token)) {
            this.accountConfiguration.setEmailConfirmed(true);
        } else
            throw new ConditionsNotFulFiledException("Email confirmation token is wrong");
    }

    public void hashPassword(Function<String, String> hashPasswordSupplier, String password) {
        this.password = hashPasswordSupplier.apply(password);
    }


    public void assignRole(AccountRole newRole) {
        this.accountRole = newRole;
    }

    public String generateNewPasswordToken(String email) {
        if (this.email.equals(email)) {
            generateNewPasswordToken();
            this.accountConfiguration.setNewPasswordTokenExpiration(LocalDateTime.now().plusMinutes(AccountConfiguration.NEW_PASSWORD_TOKEN_LIVING_MINUTES_DURATION));
            return this.accountConfiguration.getNewPasswordToken();
        } else throw new ConditionsNotFulFiledException("Emails do not match");
    }

    public void generateNewPassword(String token, String password, Function<String, String> passwordGenerator) {
        newPasswordTokenAreEqual(token);
        newPasswordTokenIsNotExpired();
        generateNewPasswordToken();
        this.accountConfiguration.setNewPasswordTokenExpiration(LocalDateTime.now());
        hashPassword(passwordGenerator, password);
    }

    private void generateNewPasswordToken() {
        this.accountConfiguration.setNewPasswordToken(UUID.randomUUID().toString());
    }

    private void newPasswordTokenAreEqual(String token) {
        if (!this.accountConfiguration.getNewPasswordToken().equals(token))
            throw new ConditionsNotFulFiledException("New password tokens do not match");
    }

    private void newPasswordTokenIsNotExpired() {
        final var now = LocalDateTime.now();
        if (now.isAfter(this.accountConfiguration.getNewPasswordTokenExpiration()))
            throw new ConditionsNotFulFiledException("Token is expired");
    }
}
