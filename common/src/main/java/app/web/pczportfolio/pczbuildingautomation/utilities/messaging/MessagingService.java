package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

public interface MessagingService {
    String createDeviceChannel(MessagingChannel messagingChannel);

    String createDeviceDirectExchange(MessagingChannel messagingChannel);

    void deleteDeviceChannel(MessagingChannel messagingChannel);
}
