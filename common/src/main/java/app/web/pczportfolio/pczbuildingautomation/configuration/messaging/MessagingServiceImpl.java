package app.web.pczportfolio.pczbuildingautomation.configuration.messaging;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class MessagingServiceImpl implements MessagingService {
    private final MessagingDirectExchangeCreator messagingDirectExchangeCreator;
    private final MessagingQueueRemover messagingQueueRemover;
    private final MessagingQueueCreator messagingQueueCreator;

    @Override
    public String createDeviceDirectExchange(MessagingChannel messagingChannel) {
        return messagingDirectExchangeCreator.createDirectExchangeWithDlx(messagingChannel);
    }

    @Override
    public void deleteDeviceChannel(MessagingChannel messagingChannel) {
        messagingQueueRemover.removeChannel(messagingChannel);
    }

    @Override
    public String createDeviceChannel(MessagingChannel messagingChannel) {
        return messagingQueueCreator.createDeviceQueue(messagingChannel);
    }
}