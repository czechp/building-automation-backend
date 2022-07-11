package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.query;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.dto.DeviceEventQueryDto;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortFindAll;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortFindById;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortFindByOwner;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortFindCurrentUser;
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

    @Override
    public List<DeviceEventQueryDto> findAllDeviceEvents(Pageable pageable) {
        return deviceEventPortFindAll.findAllDeviceEvents(pageable)
                .stream()
                .map(DeviceEventQueryDto::create)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeviceEventQueryDto> findByCurrentUser(String owner, Pageable pageable) {
        final var currentUser = deviceEventPortFindCurrentUser.findCurrentUser();
        return deviceEventPortFindByOwner.findDeviceEventsByOwner(currentUser, pageable)
                .stream()
                .map(DeviceEventQueryDto::create)
                .collect(Collectors.toList());

    }

    //TODO
    @Override
    public DeviceEventQueryDto findById(long deviceEventId) {
        return deviceEventPortFindById.findDeviceEventById(deviceEventId)
                .map(DeviceEventQueryDto::create)
                .orElseThrow(() -> new NotFoundException("Device event with deviceEventId: " + deviceEventId + " does not exist."));
    }
}
