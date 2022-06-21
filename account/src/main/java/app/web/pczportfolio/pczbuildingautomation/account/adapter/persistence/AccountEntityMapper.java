package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;

class AccountEntityMapper {
    static AccountEntity toEntity(Account account) {
        AccountEntity accountEntity = AccountEntity.builder()
                .withId(account.getId())
                .withVersion(account.getVersion())
                .withUsername(account.getUsername())
                .withPassword(account.getPassword())
                .withEmail(account.getEmail())
                .withAccountRole(account.getAccountRole())
                .withAccountConfigurationEmb(
                        AccountConfigurationEmb.builder()
                                .withAdminActivation(account.getAccountConfiguration().isAdminActivation())
                                .withEmailConfirmed(account.getAccountConfiguration().isEmailConfirmed())
                                .withEnableToken(account.getAccountConfiguration().getEnableToken())
                                .build()
                )
                .build();

        accountEntity.setId(account.getId());
        return accountEntity;
    }

    static Account toDomain(AccountEntity accountEntity) {
        return Account.mapFromEntity(accountEntity);
    }
}
