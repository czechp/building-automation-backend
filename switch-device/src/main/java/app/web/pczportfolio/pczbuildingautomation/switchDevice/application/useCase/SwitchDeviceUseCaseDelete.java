package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.location.dto.LocationFacadeDto;

public interface SwitchDeviceUseCaseDelete {
    void deleteSwitchDeviceById(long switchDeviceId);
    void deleteSwitchDevicesLocationRemoved(LocationFacadeDto locationFacadeDto);

    void deleteSwitchDevicesAccountRemoved(AccountFacadeDto accountFacadeDto);
}
