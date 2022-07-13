package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.service;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortFindCurrentUser;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortSave;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.useCase.DeviceEventUseCaseCreate;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEventType;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class DeviceEventUseCaseCreateImpl implements DeviceEventUseCaseCreate {
    private final DeviceEventPortSave deviceEventPortSave;
    private final DeviceEventPortFindCurrentUser deviceEventPortFindCurrentUser;

    @Override
    @Transactional
    public DeviceEvent createDeviceEvent(DeviceChannel deviceChannel, DeviceEventType deviceEventType) {
        final var normalDeviceEvent = DeviceEvent.createEvent(deviceChannel, deviceEventType);
        deviceEventPortSave.save(normalDeviceEvent);
        return normalDeviceEvent;
    }

    @Override
    @Transactional
    public DeviceEvent createDeviceEventFailed(DeviceEventType deviceEventType) {
        final var currentUser = deviceEventPortFindCurrentUser.findCurrentUser();
        final var failedEvent = DeviceEvent.createFailedEvent(currentUser, deviceEventType);
        deviceEventPortSave.save(failedEvent);
        return failedEvent;
    }
}
