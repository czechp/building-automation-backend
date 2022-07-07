package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;

public interface SwitchDeviceUseCaseDelete {
    void deleteSwitchDeviceById(long switchDeviceId);
    void deleteSwitchDevicesLocationRemoved(long locationId);

    void deleteSwitchDevicesAccountRemoved(AccountFacadeDto accountFacadeDto);
}
