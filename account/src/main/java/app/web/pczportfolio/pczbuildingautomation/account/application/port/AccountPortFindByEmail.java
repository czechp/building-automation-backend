package app.web.pczportfolio.pczbuildingautomation.account.application.port;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;

import java.util.Optional;

public interface AccountPortFindByEmail {
    Optional<Account> findAccountByEmail(String email);
}
