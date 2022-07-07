package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceSetStateMsgDto;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.MessagingChannel;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface SwitchDevicePortChannelSendMsg {
    void sendMsgToDeviceChannel(MessagingChannel messagingChannel, SwitchDeviceSetStateMsgDto switchDeviceSetStateMsgDto) throws JsonProcessingException;
}
