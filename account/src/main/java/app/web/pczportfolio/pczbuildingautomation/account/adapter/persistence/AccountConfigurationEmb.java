package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PACKAGE, setterPrefix = "with")
@Embeddable
public class AccountConfigurationEmb {
    private boolean adminActivation;
}
