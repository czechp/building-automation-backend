package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.EventType;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannel;

public interface DeviceEventUseCaseCreate {
    DeviceEvent createDeviceEvent(DeviceChannel deviceChannel, EventType eventType);
    DeviceEvent createDeviceEventFailed(EventType eventType);
}
