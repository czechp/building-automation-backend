package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceCreateDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;

public interface SwitchDeviceUseCaseCreate {
    SwitchDevice create(SwitchDeviceCreateDto switchDeviceCreateDto);
}
