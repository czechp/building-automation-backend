package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannelMsg;

public interface DeviceEventUseCaseRejectedMsg {
    DeviceEvent createEventFromRejectedMsg(DeviceChannelMsg deviceChannelMsg);
}
