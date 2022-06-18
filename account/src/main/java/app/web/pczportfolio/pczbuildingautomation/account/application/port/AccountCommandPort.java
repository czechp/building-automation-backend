package app.web.pczportfolio.pczbuildingautomation.account.application.port;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;

import java.util.Optional;

public interface AccountCommandPort {
    Account saveAccount(Account account);

    void deleteAccount(Account account);

    Optional<Account> findAccountById(long id);

    Optional<Account> findAccountByUsernameOrEmail(String username, String email);

    Optional<Account> findCurrentLoggedUser();

}
