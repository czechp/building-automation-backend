package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.service;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortFindCurrentUser;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortSave;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.useCase.DeviceEventUseCaseCreate;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.EventType;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class DeviceEventUseCaseCreateImpl implements DeviceEventUseCaseCreate {
    private final DeviceEventPortSave deviceEventPortSave;
    private final DeviceEventPortFindCurrentUser deviceEventPortFindCurrentUser;

    @Override
    public DeviceEvent createDeviceEvent(DeviceChannel deviceChannel, EventType eventType) {
        final var normalDeviceEvent = DeviceEvent.createEvent(deviceChannel, eventType);
        deviceEventPortSave.save(normalDeviceEvent);
        return normalDeviceEvent;
    }

    @Override
    public DeviceEvent createDeviceEventFailed(EventType eventType) {
        final var currentUser = deviceEventPortFindCurrentUser.findCurrentUser();
        final var failedEvent = DeviceEvent.createFailedEvent(currentUser, eventType);
        deviceEventPortSave.save(failedEvent);
        return failedEvent;
    }
}
