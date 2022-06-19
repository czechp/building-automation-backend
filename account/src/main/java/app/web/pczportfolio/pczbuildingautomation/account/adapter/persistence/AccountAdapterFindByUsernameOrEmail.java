package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindByUsernameOrEmail;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class AccountAdapterFindByUsernameOrEmail implements AccountPortFindByUsernameOrEmail {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Optional<Account> findAccountByUsernameOrEmail(String username, String email) {
        return accountJpaRepository.findByUsernameOrEmail(username, email)
                .map(AccountEntityMapper::toDomain);
    }
}
