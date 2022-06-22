package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;

class AccountEntityMapper {
    static AccountEntity toEntity(Account account) {
        AccountEntity accountEntity = new AccountEntity(
                account.getId(),
                account.getVersion(),
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
        return accountEntity;
    }

    static Account toDomain(AccountEntity accountEntity) {
        return Account.mapFromEntity(accountEntity);
    }
}
