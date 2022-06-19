package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;


import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindByUsername;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class AccountAdapterFindByUsername implements AccountPortFindByUsername {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Optional<Account> findAccountByUsername(String username) {
        return accountJpaRepository.findByUsername(username)
                .map(AccountEntityMapper::toDomain);
    }
}
