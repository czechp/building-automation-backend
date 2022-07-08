package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.messaging;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceSetNewStateMsgDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortChannelSendMsg;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannel;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.MessagingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SwitchDeviceMsgAdapterChannelSendMsg implements SwitchDevicePortChannelSendMsg {
    private final MessagingService messagingService;
    private final ObjectMapper objectMapper;


    @Override
    public void sendMsgToDeviceChannel(DeviceChannel deviceChannel, SwitchDeviceSetNewStateMsgDto switchDeviceSetNewStateMsgDto) throws JsonProcessingException {
        final var message = objectMapper.writeValueAsString(switchDeviceSetNewStateMsgDto);
        messagingService.sendMessageToChannel(deviceChannel, message);

    }
}
