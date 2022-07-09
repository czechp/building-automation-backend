package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;

import java.util.List;

public interface SwitchDeviceUseCaseDelete {
    SwitchDevice deleteSwitchDeviceById(long switchDeviceId);

    List<SwitchDevice> deleteSwitchDevicesLocationRemoved(long locationId);

}
