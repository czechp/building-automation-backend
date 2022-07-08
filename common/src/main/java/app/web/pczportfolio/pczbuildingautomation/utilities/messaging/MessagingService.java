package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

public interface MessagingService {
    String createDeviceChannel(DeviceChannel deviceChannel);

    String createDeviceDirectExchange(DeviceChannel deviceChannel);

    void deleteDeviceChannel(DeviceChannel deviceChannel);

    void sendMessageToChannel(DeviceChannel deviceChannel, String message);
}
