package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.service;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortSave;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.useCase.DeviceEventUseCaseRejectedMsg;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannelMsg;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class DeviceEventUseCaseRejectedMsgImpl implements DeviceEventUseCaseRejectedMsg {
    private final DeviceEventPortSave deviceEventPortSave;

    @Override
    public DeviceEvent createEventFromRejectedMsg(DeviceChannelMsg deviceChannelMsg) {
        final var deviceEventRejectedMsg = DeviceEvent.createFailedEvent(deviceChannelMsg);
        deviceEventPortSave.save(deviceEventRejectedMsg);
        return deviceEventRejectedMsg;
    }
}
