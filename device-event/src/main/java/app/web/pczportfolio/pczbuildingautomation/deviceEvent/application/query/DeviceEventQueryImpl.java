package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.query;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.dto.DeviceEventQueryDto;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.*;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.service.DeviceEventOwnerValidator;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class DeviceEventQueryImpl implements DeviceEventQuery {
    private final DeviceEventPortFindAll deviceEventPortFindAll;
    private final DeviceEventPortFindByOwner deviceEventPortFindByOwner;
    private final DeviceEventPortFindCurrentUser deviceEventPortFindCurrentUser;
    private final DeviceEventPortFindById deviceEventPortFindById;
    private final DeviceEventOwnerValidator deviceEventOwnerValidator;
    private final DeviceEventPortFindByOwnerAndDeviceId deviceEventPortFindByOwnerAndDeviceId;

    @Override
    public List<DeviceEventQueryDto> findAllDeviceEvents(Pageable pageable) {
        return deviceEventPortFindAll.findAllDeviceEvents(pageable)
                .stream()
                .map(DeviceEventQueryDto::create)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeviceEventQueryDto> findByCurrentUser(Pageable pageable) {
        final var currentUser = deviceEventPortFindCurrentUser.findCurrentUser();
        return deviceEventPortFindByOwner.findDeviceEventsByOwner(currentUser, pageable)
                .stream()
                .map(DeviceEventQueryDto::create)
                .collect(Collectors.toList());

    }

    @Override
    public DeviceEventQueryDto findById(long deviceEventId) {
        final var deviceEvent = deviceEventPortFindById.findDeviceEventById(deviceEventId)
                .orElseThrow(() -> new NotFoundException("Device event with deviceEventId: " + deviceEventId + " does not exist."));
        deviceEventOwnerValidator.validateDeviceEventOwningOrThrowException(deviceEvent);
        return DeviceEventQueryDto.create(deviceEvent);
    }

    @Override
    public List<DeviceEventQueryDto> findByDeviceId(long deviceId) {
        final var currentUser = deviceEventPortFindCurrentUser.findCurrentUser();
        return deviceEventPortFindByOwnerAndDeviceId.findDeviceEventsByOwnerAndDeviceId(currentUser, deviceId)
                .stream()
                .map(DeviceEventQueryDto::create)
                .collect(Collectors.toList());
    }
}
