package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class MessagingServiceImpl implements MessagingService {
    private final MessagingDirectExchangeCreator messagingDirectExchangeCreator;
    private final MessagingQueueRemover messagingQueueRemover;
    private final MessagingQueueCreator messagingQueueCreator;

    private final MessagingMessageSender messagingMessageSender;
    @Override
    public String createDeviceDirectExchange(DeviceChannel deviceChannel) {
        return messagingDirectExchangeCreator.createDirectExchangeWithDlx(deviceChannel);
    }

    @Override
    public void deleteDeviceChannel(DeviceChannel deviceChannel) {
        messagingQueueRemover.removeChannel(deviceChannel);
    }

    @Override
    public String createDeviceChannel(DeviceChannel deviceChannel) {
        return messagingQueueCreator.createDeviceQueue(deviceChannel);
    }

    @Override
    public void sendMessageToChannel(DeviceChannel deviceChannel, DeviceChannelMsg deviceChannelMsg) throws JsonProcessingException {
        messagingMessageSender.sendMessageToQueue(deviceChannel, deviceChannelMsg);
    }
}
