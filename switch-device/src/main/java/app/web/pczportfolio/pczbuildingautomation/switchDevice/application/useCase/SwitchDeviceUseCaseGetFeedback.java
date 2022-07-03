package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceFeedbackDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;

public interface SwitchDeviceUseCaseGetFeedback {
    SwitchDevice receiveFeedbackFromDevice(SwitchDeviceFeedbackDto switchDeviceFeedbackDto);
}
