package app.web.pczportfolio.pczbuildingautomation.configuration.messaging;

public interface MessagingService {
    String createDeviceDirectExchange(MessagingChannel messagingChannel);
    void deleteDeviceChannel(MessagingChannel messagingChannel);
}
