package app.web.pczportfolio.pczbuildingautomation.account.domain;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountConfigurationEmb;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class AccountConfiguration {
    public static final int NEW_PASSWORD_TOKEN_LIVING_MINUTES_DURATION = 10;
    private boolean adminActivation;
    private String enableToken;
    private boolean emailConfirmed;
    private String newPasswordToken;
    private LocalDateTime newPasswordTokenExpiration;


    AccountConfiguration() {
        this.adminActivation = false;
        this.enableToken = UUID.randomUUID().toString();
        this.emailConfirmed = false;
        this.newPasswordToken = "";
        this.newPasswordTokenExpiration = LocalDateTime.now();
    }

    static AccountConfiguration mapFromEntity(AccountConfigurationEmb accountConfigurationEmb) {
        return AccountConfiguration.builder()
                .withAdminActivation(accountConfigurationEmb.isAdminActivation())
                .withEnableToken(accountConfigurationEmb.getEnableToken())
                .withEmailConfirmed(accountConfigurationEmb.isEmailConfirmed())
                .withNewPasswordToken(accountConfigurationEmb.getNewPasswordToken())
                .withNewPasswordTokenExpiration(accountConfigurationEmb.getNewPasswordTokenExpiration())
                .build();
    }
}
