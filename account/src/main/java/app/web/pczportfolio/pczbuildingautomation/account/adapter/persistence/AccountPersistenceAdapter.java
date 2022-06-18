package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountCommandPort;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityUserDetailsService;
import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityUtilities;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class AccountPersistenceAdapter implements AccountCommandPort, SecurityUserDetailsService {
    private final AccountJpaRepository accountJpaRepository;
    private final SecurityUtilities securityUtilities;

    @Override
    public Account saveAccount(Account account) {
        return AccountEntityMapper.toDomain(accountJpaRepository.save(AccountEntityMapper.toEntity(account)));
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
                .map(Account::mapFromEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountJpaRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }

    @Override
    public void deleteAccount(Account account) {
        accountJpaRepository.delete(AccountEntityMapper.toEntity(account));
    }

    @Override
    public Optional<Account> findCurrentLoggedUser() {
        String currentLoggedUsername = securityUtilities.getCurrentUser();
        return accountJpaRepository.findByUsername(currentLoggedUsername)
                .map(AccountEntityMapper::toDomain);
    }

    @Override
    public Optional<Account> findAccountByEnableToken(String token) {
        return accountJpaRepository.findByEnableToken(token)
                .map(AccountEntityMapper::toDomain);

    }
}
