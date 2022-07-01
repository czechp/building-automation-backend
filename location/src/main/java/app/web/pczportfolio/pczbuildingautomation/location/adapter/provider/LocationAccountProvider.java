package app.web.pczportfolio.pczbuildingautomation.location.adapter.provider;

import app.web.pczportfolio.pczbuildingautomation.account.AccountFacade;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindAccountByUsername;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindCurrentUserAccount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class LocationAccountProvider implements LocationPortFindCurrentUserAccount, LocationPortFindAccountByUsername {
    private final AccountFacade accountFacade;

    @Override
    public Optional<AccountFacadeDto> findAccountOfCurrentUser() {
        return accountFacade.findCurrentUserAccount();
    }

    @Override
    public Optional<AccountFacadeDto> findAccountByUsername(String username) {
        return accountFacade.findAccountByUsername(username);
    }
}
