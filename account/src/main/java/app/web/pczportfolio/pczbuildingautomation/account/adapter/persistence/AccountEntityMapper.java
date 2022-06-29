package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.account.domain.AccountConfiguration;

class AccountEntityMapper {
    static AccountEntity toEntity(Account account) {
        return new AccountEntity(
                account.getId(),
                account.getVersion(),
                account.getCreationTimestamp(),
                account.getUsername(),
                account.getPassword(),
                account.getEmail(),
                account.getAccountRole(),
                new AccountConfigurationEmb(
                        account.getAccountConfiguration().isAdminActivation(),
                        account.getAccountConfiguration().getEnableToken(),
                        account.getAccountConfiguration().isEmailConfirmed(),
                        account.getAccountConfiguration().getNewPasswordToken(),
                        account.getAccountConfiguration().getNewPasswordTokenExpiration()
                )
        );
    }

    static Account toDomain(AccountEntity accountEntity) {
        return Account.builder()
                .withId(accountEntity.getId())
                .withVersion(accountEntity.getVersion())
                .withCreationTimestamp(accountEntity.getCreationTimestamp())
                .withUsername(accountEntity.getUsername())
                .withPassword(accountEntity.getPassword())
                .withEmail(accountEntity.getEmail())
                .withAccountRole(accountEntity.getAccountRole())
                .withAccountConfiguration(
                        AccountConfiguration.builder()
                                .withAdminActivation(accountEntity.getAccountConfigurationEmb().isAdminActivation())
                                .withEnableToken(accountEntity.getAccountConfigurationEmb().getEnableToken())
                                .withEmailConfirmed(accountEntity.getAccountConfigurationEmb().isEmailConfirmed())
                                .withNewPasswordToken(accountEntity.getAccountConfigurationEmb().getNewPasswordToken())
                                .withNewPasswordTokenExpiration(accountEntity.getAccountConfigurationEmb().getNewPasswordTokenExpiration())
                                .build()
                )
                .build();
    }
}
