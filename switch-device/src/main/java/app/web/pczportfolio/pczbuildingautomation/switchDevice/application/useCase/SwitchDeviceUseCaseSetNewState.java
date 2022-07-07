package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceSetNewStateDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;

public interface SwitchDeviceUseCaseSetNewState  {
    SwitchDevice setNewStateForSwitchDevice(SwitchDeviceSetNewStateDto switchDeviceSetNewStateDto);
}
