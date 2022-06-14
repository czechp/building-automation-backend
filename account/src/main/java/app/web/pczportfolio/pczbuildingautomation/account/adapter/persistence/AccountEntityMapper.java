package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;

class AccountEntityMapper {
    static AccountEntity toEntity(Account account) {
        AccountEntity accountEntity = new AccountEntity(
                account.getUsername(),
                account.getPassword(),
                account.getEmail(),
                account.getEnableToken()
        );
        accountEntity.setId(account.getId());
        return accountEntity;
    }
}
