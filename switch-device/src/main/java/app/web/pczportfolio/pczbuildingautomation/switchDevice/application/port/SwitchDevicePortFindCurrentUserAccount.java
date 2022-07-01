package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;

import java.util.Optional;

public interface SwitchDevicePortFindCurrentUserAccount {
    Optional<AccountFacadeDto> findCurrentUserAccount();
}
