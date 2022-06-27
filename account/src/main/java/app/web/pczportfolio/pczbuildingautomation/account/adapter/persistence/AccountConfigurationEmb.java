package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder(setterPrefix = "with")
public class AccountConfigurationEmb {
    private boolean adminActivation;
    @NotBlank(message = "Enable token cannot be blank")
    private String enableToken;
    private boolean emailConfirmed;
    private String newPasswordToken;
    private LocalDateTime newPasswordTokenExpiration;
}
