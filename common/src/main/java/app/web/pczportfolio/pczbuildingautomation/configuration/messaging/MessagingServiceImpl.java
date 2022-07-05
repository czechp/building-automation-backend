package app.web.pczportfolio.pczbuildingautomation.configuration.messaging;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class MessagingServiceImpl implements MessagingService {
    private final MessagingDirectExchangeCreator messagingDirectExchangeCreator;
    private final MessagingChannelRemover messagingChannelRemover;

    @Override
    public String createDeviceDirectExchange(MessagingChannel messagingChannel) {
        return messagingDirectExchangeCreator.createDirectExchange(messagingChannel);
    }

    @Override
    public void deleteDeviceChannel(MessagingChannel messagingChannel) {
        messagingChannelRemover.removeChannel(messagingChannel);
    }
}
