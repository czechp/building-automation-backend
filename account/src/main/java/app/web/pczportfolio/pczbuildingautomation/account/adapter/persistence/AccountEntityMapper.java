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
                .withEnableToken(account.getEnableToken())
                .withEmailConfirmed(account.isEmailConfirmed())
                .withAccountRole(account.getAccountRole())
                .withAccountConfigurationEmb(
                        AccountConfigurationEmb.builder()
                                .withAdminActivation(account.isAdminActivation())
                                .build()
                )
                .build();
//                new AccountEntity(
//                account.getId(),
//                account.getVersion(),
//                account.getUsername(),
//                account.getPassword(),
//                account.getEmail(),
//                account.getEnableToken(),
//                account.isEmailConfirmed(),
//                account.isEmailConfirmed(),
//                account.getAccountRole()
//        );
        accountEntity.setId(account.getId());
        return accountEntity;
    }

    static Account toDomain(AccountEntity accountEntity) {
        return Account.mapFromEntity(accountEntity);
    }
}
