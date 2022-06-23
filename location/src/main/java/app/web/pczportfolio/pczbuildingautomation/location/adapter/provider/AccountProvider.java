package app.web.pczportfolio.pczbuildingautomation.location.adapter.provider;

import app.web.pczportfolio.pczbuildingautomation.account.AccountFacade;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindAccountById;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class AccountProvider implements LocationPortFindAccountById {
    private final AccountFacade accountFacade;

    @Override
    public Optional<AccountFacadeDto> findAccountById(long accountId) {
        return accountFacade.findAccountFacadeDtoById(accountId);
    }
}
