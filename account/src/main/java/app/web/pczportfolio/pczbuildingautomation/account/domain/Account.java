package app.web.pczportfolio.pczbuildingautomation.account.domain;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountEntity;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountCommandDto;
import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiled;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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



    public static Account create(AccountCommandDto dto) {
        comparePasswords(dto.getPassword(), dto.getPasswordConfirm());
        return new Account(dto.getUsername(), dto.getPassword(), dto.getEmail());
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
            throw new ConditionsNotFulFiled("Passwords are not equal");
    }

    public void adminActivate() {
        this.adminActivation = true;
    }

    public void adminDeactivate() {
        this.adminActivation = false;
    }
}
