package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEventType;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannel;

public interface DeviceEventUseCaseCreate {
    DeviceEvent createDeviceEvent(DeviceChannel deviceChannel, DeviceEventType deviceEventType);
    DeviceEvent createDeviceEventFailed(DeviceEventType deviceEventType);
}
