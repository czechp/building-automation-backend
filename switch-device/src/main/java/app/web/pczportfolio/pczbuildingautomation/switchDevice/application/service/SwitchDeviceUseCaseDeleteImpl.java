package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.exception.NotEnoughPrivilegesException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortDelete;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindById;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseDelete;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SwitchDeviceUseCaseDeleteImpl implements SwitchDeviceUseCaseDelete {
    private final SwitchDevicePortFindById switchDevicePortFindById;
    private final SwitchDevicePortDelete switchDevicePortDelete;
    private final SwitchDeviceOwnerValidator switchDeviceOwnerValidator;

    @Override
    public void deleteSwitchDevice(long switchDeviceId) {
        final SwitchDevice switchDevice = getSwitchDevice(switchDeviceId);
        validateOwning(switchDevice);
        switchDevicePortDelete.deleteSwitchDevice(switchDevice);
    }

    private SwitchDevice getSwitchDevice(long switchDeviceId) {
        return switchDevicePortFindById.findSwitchDeviceById(switchDeviceId)
                .orElseThrow(() -> new NotFoundException("Switch device with id: " + switchDeviceId + " does not exist"));
    }

    private void validateOwning(SwitchDevice switchDevice) {
        if (!switchDeviceOwnerValidator.currentUserIsOwner(switchDevice))
            throw new NotEnoughPrivilegesException("You are not owner of switch device with id: " + switchDevice.getId());
    }
}
