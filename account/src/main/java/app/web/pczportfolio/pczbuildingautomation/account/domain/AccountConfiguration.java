package app.web.pczportfolio.pczbuildingautomation.account.domain;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountConfigurationEmb;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Builder(setterPrefix = "with")
public  class AccountConfiguration {
    private boolean adminActivation;
    private String enableToken;
    private boolean emailConfirmed;

     AccountConfiguration() {
        this.adminActivation = false;
        this.enableToken = UUID.randomUUID().toString();
        this.emailConfirmed =  false;
    }

    static AccountConfiguration mapFromEntity(AccountConfigurationEmb accountConfigurationEmb){
         return AccountConfiguration.builder()
                 .withAdminActivation(accountConfigurationEmb.isAdminActivation())
                 .withEnableToken(accountConfigurationEmb.getEnableToken())
                 .withEmailConfirmed(accountConfigurationEmb.isEmailConfirmed())
                 .build();
    }
}
