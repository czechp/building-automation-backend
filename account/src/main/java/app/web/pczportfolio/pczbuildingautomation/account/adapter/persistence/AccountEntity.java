package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Member;

@Entity
@Table(name = "accounts")
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode()
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Incorrect email format")
    private String email;

    @NotBlank(message = "Enable token cannot be blank")
    private String enableToken;
    AccountEntity() {
    }

    AccountEntity(String username, String password, String email, String enableToken) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enableToken = enableToken;
    }
}
