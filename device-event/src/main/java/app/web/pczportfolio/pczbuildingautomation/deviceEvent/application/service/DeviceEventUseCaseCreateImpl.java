package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.service;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.useCase.DeviceEventUseCaseCreate;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.EventType;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannel;
import org.springframework.stereotype.Service;

@Service
class DeviceEventUseCaseCreateImpl implements DeviceEventUseCaseCreate {
    @Override
    public DeviceEvent createDeviceEvent(DeviceChannel deviceChannel, EventType eventType) {
        return null;
    }

    @Override
    public DeviceEvent createDeviceEventFailed(String user, EventType eventType) {
        return null;
    }
}
