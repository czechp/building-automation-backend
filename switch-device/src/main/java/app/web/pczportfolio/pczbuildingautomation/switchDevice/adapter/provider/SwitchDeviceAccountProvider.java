package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.provider;

import app.web.pczportfolio.pczbuildingautomation.account.AccountFacade;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindCurrentUserAccount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class SwitchDeviceAccountProvider implements SwitchDevicePortFindCurrentUserAccount {
    private final AccountFacade accountFacade;

    @Override
    public Optional<AccountFacadeDto> findCurrentUserAccount() {
        return accountFacade.findCurrentUserAccount();
    }
}
