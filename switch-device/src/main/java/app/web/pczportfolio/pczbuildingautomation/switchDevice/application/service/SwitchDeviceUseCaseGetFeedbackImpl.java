package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceFeedbackDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindById;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseGetFeedback;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SwitchDeviceUseCaseGetFeedbackImpl implements SwitchDeviceUseCaseGetFeedback {
    private final SwitchDevicePortFindById switchDevicePortFindById;

    @Override
    public SwitchDevice receiveFeedbackFromDevice(SwitchDeviceFeedbackDto switchDeviceFeedbackDto) {
        return null;
    }
}
