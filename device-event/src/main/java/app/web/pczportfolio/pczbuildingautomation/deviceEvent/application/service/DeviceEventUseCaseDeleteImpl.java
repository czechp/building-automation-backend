package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.service;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortDelete;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortFindById;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.useCase.DeviceEventUseCaseDelete;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class DeviceEventUseCaseDeleteImpl implements DeviceEventUseCaseDelete {
    private final DeviceEventPortFindById deviceEventPortFindById;
    private final DeviceEventPortDelete deviceEventPortDelete;

    @Override
    @Transactional
    public DeviceEvent deleteDeviceEvent(long deviceEventId) {
        final var deviceEventToDelete = getDeviceEventOrThrowException(deviceEventId);
        deviceEventPortDelete.deleteDeviceEvent(deviceEventToDelete);
        return deviceEventToDelete;
    }

    private DeviceEvent getDeviceEventOrThrowException(long deviceEventId) {
        return deviceEventPortFindById.findDeviceEventById(deviceEventId)
                .orElseThrow(() -> new NotFoundException("Device event with id: " + deviceEventId + " does not exist."));
    }
}
