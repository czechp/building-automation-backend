package app.web.pczportfolio.pczbuildingautomation.account.domain;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountConfigurationEmb;
import lombok.*;

@Getter
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Builder(setterPrefix = "with")
public  class AccountConfiguration {
    private boolean adminActivation;

     AccountConfiguration() {
        this.adminActivation = false;
    }

    static AccountConfiguration mapFromEntity(AccountConfigurationEmb accountConfigurationEmb){
         return AccountConfiguration.builder()
                 .withAdminActivation(accountConfigurationEmb.isAdminActivation())
                 .build();
    }
}
