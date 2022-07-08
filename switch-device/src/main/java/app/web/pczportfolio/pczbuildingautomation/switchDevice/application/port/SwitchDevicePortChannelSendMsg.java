package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceSetNewStateMsgDto;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannel;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface SwitchDevicePortChannelSendMsg {
    void sendMsgToDeviceChannel(DeviceChannel deviceChannel, SwitchDeviceSetNewStateMsgDto switchDeviceSetNewStateMsgDto) throws JsonProcessingException;
}
