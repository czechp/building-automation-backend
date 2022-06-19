package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindById;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class AccountAdapterFindById implements AccountPortFindById {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Optional<Account> findAccountById(long id) {
        return accountJpaRepository.findById(id)
                .map(AccountEntityMapper::toDomain);
    }
}
