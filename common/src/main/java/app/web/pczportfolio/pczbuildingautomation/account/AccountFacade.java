package app.web.pczportfolio.pczbuildingautomation.account;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;

import java.util.Optional;

public interface AccountFacade {
    Optional<AccountFacadeDto> findCurrentUserAccount();

    Optional<AccountFacadeDto> findAccountByUsername(String username);
}
