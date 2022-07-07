package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceSetNewStateDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface SwitchDeviceUseCaseSetNewState  {
    SwitchDevice setNewStateForSwitchDevice(SwitchDeviceSetNewStateDto switchDeviceSetNewStateDto) throws JsonProcessingException;
}
