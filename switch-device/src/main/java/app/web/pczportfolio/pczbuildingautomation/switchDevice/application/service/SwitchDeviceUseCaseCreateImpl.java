package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.annotation.DeviceEventCreate;
import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiledException;
import app.web.pczportfolio.pczbuildingautomation.location.dto.LocationFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceCreateDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortChannelCreate;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindLocationByIdAndCurrentUser;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortSave;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseCreate;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class SwitchDeviceUseCaseCreateImpl implements SwitchDeviceUseCaseCreate {
    private final SwitchDevicePortSave switchDevicePortSave;
    private final SwitchDevicePortFindLocationByIdAndCurrentUser switchDevicePortFindLocationByIdAndCurrentUser;
    private final SwitchDevicePortChannelCreate switchDevicePortChannelCreate;

    @DeviceEventCreate
    @Override
    @Transactional
    public SwitchDevice createSwitchDevice(SwitchDeviceCreateDto switchDeviceCreateDto) {
        final var locationToAssign = findLocationToAssign(switchDeviceCreateDto);
        final var createdSwitchDevice = SwitchDevice.create(switchDeviceCreateDto, locationToAssign);
        final var savedSwitchDevice = switchDevicePortSave.saveSwitchDevice(createdSwitchDevice);
        switchDevicePortChannelCreate.createChannelForSwitchDevice(savedSwitchDevice);
        return createdSwitchDevice;
    }

    private LocationFacadeDto findLocationToAssign(SwitchDeviceCreateDto switchDeviceCreateDto) {
        return switchDevicePortFindLocationByIdAndCurrentUser.findLocationByIdAndCurrentUser(switchDeviceCreateDto.getLocationId())
                .orElseThrow(() -> new ConditionsNotFulFiledException("Location with id: " + switchDeviceCreateDto.getLocationId() +
                        " does not exists or you are not owner of this"));
    }
}
