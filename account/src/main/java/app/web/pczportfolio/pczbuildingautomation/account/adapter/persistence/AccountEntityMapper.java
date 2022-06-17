package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;

class AccountEntityMapper {
    static AccountEntity toEntity(Account account) {
        AccountEntity accountEntity = new AccountEntity(
                account.getUsername(),
                account.getPassword(),
                account.getEmail(),
                account.getEnableToken(),
                account.getAccountRole()
        );
        accountEntity.setId(account.getId());
        return accountEntity;
    }

    static Account toDomain(AccountEntity accountEntity) {
        return Account.mapFromEntity(accountEntity);
    }
}
