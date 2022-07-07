package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.*;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseDelete;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
class SwitchDeviceUseCaseDeleteImpl implements SwitchDeviceUseCaseDelete {
    private final SwitchDevicePortFindById switchDevicePortFindById;
    private final SwitchDevicePortFindByLocationId switchDevicePortFindByLocationId;
    private final SwitchDevicePortFindByOwner switchDevicePortFindByOwner;
    private final SwitchDeviceOwnerValidator switchDeviceOwnerValidator;
    private final SwitchDevicePortDelete switchDevicePortDelete;
    private final SwitchDevicePortDeleteChannel switchDevicePortDeleteChannel;

    @Override
    public void deleteSwitchDeviceById(long switchDeviceId) {
        final SwitchDevice switchDevice = getSwitchDevice(switchDeviceId);
        switchDeviceOwnerValidator.currentUserIsOwnerOrElseThrowException(switchDevice);
        deleteSwitchDevice(switchDevice);
    }

    @Override
    public void deleteSwitchDevicesLocationRemoved(long locationId) {
        switchDevicePortFindByLocationId.findSwitchDevicesByLocationId(locationId)
                .forEach(this::deleteSwitchDevice);
    }

    @Override
    public void deleteSwitchDevicesAccountRemoved(AccountFacadeDto accountFacadeDto) {
        switchDevicePortFindByOwner.findSwitchDevicesByOwner(accountFacadeDto.getUsername(), Pageable.unpaged())
                .forEach(this::deleteSwitchDevice);
    }

    private SwitchDevice getSwitchDevice(long switchDeviceId) {
        return switchDevicePortFindById.findSwitchDeviceById(switchDeviceId)
                .orElseThrow(() -> new NotFoundException("Switch device with id: " + switchDeviceId + " does not exist"));
    }


    void deleteSwitchDevice(SwitchDevice switchDevice) {
        switchDevicePortDelete.deleteSwitchDevice(switchDevice);
        switchDevicePortDeleteChannel.deleteSwitchDeviceChannel(switchDevice);
    }

}
