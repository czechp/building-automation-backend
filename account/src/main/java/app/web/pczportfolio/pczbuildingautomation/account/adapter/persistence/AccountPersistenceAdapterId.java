package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountCreatePort;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountDeletePort;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountFindByIdPort;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountFindByUsernameOrEmailPort;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class AccountPersistenceAdapterId implements
        AccountCreatePort,
        AccountFindByUsernameOrEmailPort,
        AccountFindByIdPort,
        AccountDeletePort {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public void createAccount(Account account) {
        accountJpaRepository.save(AccountEntityMapper.toEntity(account));
    }

    @Override
    public Optional<Account> findAccountByUsernameOrEmail(String username, String email) {
        return accountJpaRepository
                .findByUsernameOrEmail(username, email)
                .map(AccountEntityMapper::toDomain);
    }

    @Override
    public Optional<Account> findAccountById(long id) {
        return accountJpaRepository.findById(id)
                .map(Account::createFromEntity);
    }

    @Override
    public void deleteAccount(Account account) {
        accountJpaRepository.delete(AccountEntityMapper.toEntity(account));
    }
}
