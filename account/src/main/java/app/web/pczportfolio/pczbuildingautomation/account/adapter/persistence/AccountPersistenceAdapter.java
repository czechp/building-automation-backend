package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountCreatePort;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AccountPersistenceAdapter implements AccountCreatePort {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public void createAccount(Account account) {
        accountJpaRepository.save(AccountEntityMapper.toEntity(account));
    }
}
