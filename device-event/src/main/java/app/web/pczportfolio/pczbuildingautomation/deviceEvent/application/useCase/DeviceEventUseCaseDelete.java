package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;

public interface DeviceEventUseCaseDelete {
    DeviceEvent deleteDeviceEvent(long deviceEventId);
}
