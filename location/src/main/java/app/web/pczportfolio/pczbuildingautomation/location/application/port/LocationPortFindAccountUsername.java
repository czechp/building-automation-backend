package app.web.pczportfolio.pczbuildingautomation.location.application.port;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;

import java.util.Optional;

public interface LocationPortFindAccountUsername {
    Optional<AccountFacadeDto> findAccountByUsername(String username);
}
