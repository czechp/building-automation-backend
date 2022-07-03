package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindById;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortSave;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseCheckError;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class SwitchDeviceUseCaseCheckErrorImpl implements SwitchDeviceUseCaseCheckError {
    private final SwitchDevicePortFindById switchDevicePortFindById;
    private final SwitchDevicePortSave switchDevicePortSave;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SwitchDevice checkSwitchDeviceError(long switchDeviceId) {
        SwitchDevice switchDeviceToExamine = fetchSwitchDeviceByIdOrThrowException(switchDeviceId);
        switchDeviceToExamine.checkDeviceError();
        switchDevicePortSave.saveSwitchDevice(switchDeviceToExamine);
        return switchDeviceToExamine;
    }

    private SwitchDevice fetchSwitchDeviceByIdOrThrowException(long switchDeviceId) {
        return switchDevicePortFindById.findSwitchDeviceById(switchDeviceId)
                .orElseThrow(() -> new NotFoundException("Switch device with id: " + switchDeviceId + " does not exists"));
    }
}
