package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindById;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseCheckError;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SwitchDeviceUseCaseCheckErrorImpl implements SwitchDeviceUseCaseCheckError {
    private final SwitchDevicePortFindById switchDevicePortFindById;

    @Override
    public SwitchDevice checkSwitchDeviceError(long switchDeviceId) {
        return null;
    }
}
