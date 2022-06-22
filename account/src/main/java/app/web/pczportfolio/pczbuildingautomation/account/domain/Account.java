package app.web.pczportfolio.pczbuildingautomation.account.domain;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountEntity;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountCreateCmdDto;
import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiledException;
import lombok.*;

import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PUBLIC, setterPrefix = "with")
public class Account {
    private long id;
    private long version;
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
        return newAccount;
    }

    public static Account mapFromEntity(AccountEntity entity) {
        return Account.builder()
                .withId(entity.getId())
                .withUsername(entity.getUsername())
                .withPassword(entity.getPassword())
                .withEmail(entity.getEmail())
                .withVersion(entity.getVersion())
                .withAccountRole(entity.getAccountRole())
                .withAccountConfiguration(AccountConfiguration.mapFromEntity(entity.getAccountConfigurationEmb()))
                .build();

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

    public void hashPassword(Function<String, String> hashPasswordSupplier) {
        this.password = hashPasswordSupplier.apply(this.password);
    }


    public void assignRole(AccountRole newRole) {
        this.accountRole = newRole;
    }
}
