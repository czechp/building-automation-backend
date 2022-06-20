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
    private String username;
    private String password;
    private String email;
    private String enableToken;
    private boolean adminActivation;
    private boolean emailConfirmed;
    private AccountRole accountRole;
    private long version;

    Account(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enableToken = UUID.randomUUID().toString();
        this.accountRole = AccountRole.USER;
    }


    public static Account create(AccountCreateCmdDto dto, Function<String, String> hashPasswordFunction) {
        comparePasswords(dto.getPassword(), dto.getPasswordConfirm());
        Account newAccount = new Account(dto.getUsername(), dto.getPassword(), dto.getEmail());
        newAccount.setPassword(hashPasswordFunction.apply(dto.getPassword()));
        return newAccount;
    }

    public static Account mapFromEntity(AccountEntity entity) {
        Account account = new Account(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getEnableToken(),
                entity.isAdminConfirmed(),
                entity.isEmailConfirmed(),
                entity.getAccountRole(),
                entity.getVersion()
        );
        return account;
    }


    private static void comparePasswords(String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm))
            throw new ConditionsNotFulFiledException("Passwords are not equal");
    }

    public Account adminActivate() {
        this.adminActivation = true;
        return this;
    }

    public Account adminDeactivate() {
        this.adminActivation = false;
        return this;
    }

    public void confirmEmail(String token) {
        if (this.enableToken.equals(token)) {
            this.setEmailConfirmed(true);
            return this;
        } else
            throw new ConditionsNotFulFiledException("Email confirmation token is wrong");
    }

    public void hashPassword(Function<String, String> hashPasswordSupplier) {
        this.password = hashPasswordSupplier.apply(this.password);
    }
}
