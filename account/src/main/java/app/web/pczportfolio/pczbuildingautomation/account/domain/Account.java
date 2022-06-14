package app.web.pczportfolio.pczbuildingautomation.account.domain;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountEntity;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountCommandDto;
import app.web.pczportfolio.pczbuildingautomation.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
public class Account {
    private long id;
    private String username;
    private String password;

    private String email;

    private String enableToken;

    Account(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enableToken = UUID.randomUUID().toString();
    }

    public static Account createFromCommandDto(AccountCommandDto dto) {
        comparePasswords(dto.getPassword(), dto.getPasswordConfirm());
        return new Account(dto.getUsername(), dto.getPassword(), dto.getEmail());
    }

    public static Account createFromEntity(AccountEntity entity) {
        Account account = new Account(entity.getUsername(), entity.getPassword(), entity.getEmail());
        account.setId(entity.getId());
        return  account;
    }


    private static void comparePasswords(String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm))
            throw new BadRequestException("Passwords are not equal");
    }
}
