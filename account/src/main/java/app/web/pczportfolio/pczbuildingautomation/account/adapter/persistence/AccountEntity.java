package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Table(name = "accounts")
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode()
public class AccountEntity implements UserDetails {
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

    private boolean adminConfirmed;

    private boolean emailConfirmed;

    AccountEntity() {
    }

    AccountEntity(String username, String password, String email, String enableToken) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enableToken = enableToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
