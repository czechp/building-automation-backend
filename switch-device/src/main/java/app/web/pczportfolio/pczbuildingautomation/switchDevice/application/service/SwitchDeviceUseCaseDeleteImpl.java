package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.annotation.DeleteDeviceEvent;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.*;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseDelete;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
class SwitchDeviceUseCaseDeleteImpl implements SwitchDeviceUseCaseDelete {
    private final SwitchDevicePortFindById switchDevicePortFindById;
    private final SwitchDevicePortFindByLocationId switchDevicePortFindByLocationId;
    private final SwitchDevicePortFindByOwner switchDevicePortFindByOwner;
    private final SwitchDeviceOwnerValidator switchDeviceOwnerValidator;
    private final SwitchDevicePortDelete switchDevicePortDelete;
    private final SwitchDevicePortChannelDelete switchDevicePortChannelDelete;


    @Override
    @DeleteDeviceEvent
    public SwitchDevice deleteSwitchDeviceById(long switchDeviceId) {
        final SwitchDevice switchDevice = getSwitchDevice(switchDeviceId);
        switchDeviceOwnerValidator.currentUserIsOwnerOrElseThrowException(switchDevice);
        deleteSwitchDevice(switchDevice);
        return switchDevice;
    }

    @Override
    @DeleteDeviceEvent
    public List<SwitchDevice> deleteSwitchDevicesLocationRemoved(long locationId) {
        List<SwitchDevice> switchDevicesToDelete = switchDevicePortFindByLocationId.findSwitchDevicesByLocationId(locationId);
        switchDevicesToDelete
                .forEach(this::deleteSwitchDevice);
        return switchDevicesToDelete;
    }


    private SwitchDevice getSwitchDevice(long switchDeviceId) {
        return switchDevicePortFindById.findSwitchDeviceById(switchDeviceId)
                .orElseThrow(() -> new NotFoundException("Switch device with id: " + switchDeviceId + " does not exist"));
    }


    void deleteSwitchDevice(SwitchDevice switchDevice) {
        switchDevicePortDelete.deleteSwitchDevice(switchDevice);
        switchDevicePortChannelDelete.deleteSwitchDeviceChannel(switchDevice);
    }

}
