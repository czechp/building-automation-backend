package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "accounts")
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode()
@AllArgsConstructor
public class AccountEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private long version;
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

    private boolean adminConfirmed;

    private boolean emailConfirmed;

    @NotNull(message = "Role cannot be null")
    @Enumerated(value = EnumType.STRING)
    private AccountRole accountRole;

    AccountEntity() {
    }


    public AccountEntity(String username, String password, String email, String enableToken, AccountRole accountRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enableToken = enableToken;
        this.accountRole = accountRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.accountRole.toString()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.adminConfirmed && emailConfirmed;
    }
}
