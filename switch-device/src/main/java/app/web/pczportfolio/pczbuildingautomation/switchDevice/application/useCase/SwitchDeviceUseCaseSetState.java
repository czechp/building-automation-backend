package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceSetStateDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;

public interface SwitchDeviceUseCaseSetState {
    SwitchDevice setStateForSwitchDevice(SwitchDeviceSetStateDto switchDeviceSetStateDto);
}
