package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PACKAGE, setterPrefix = "with")
@Embeddable
public class AccountConfigurationEmb {
    private boolean adminActivation;
    @NotBlank(message = "Enable token cannot be blank")
    private String enableToken;
    private boolean emailConfirmed;
}
