package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountCreatePort;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountFindByUsernameOrEmailPort;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class AccountPersistenceAdapter implements AccountCreatePort, AccountFindByUsernameOrEmailPort {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public void createAccount(Account account) {
        accountJpaRepository.save(AccountEntityMapper.toEntity(account));
    }

    @Override
    public Optional<Account> findByUsernameOrEmail(String username, String email) {
        return accountJpaRepository.findByUsernameOrEmail(username, email);
    }
}
