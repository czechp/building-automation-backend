package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface MessagingService {
    String createDeviceChannel(DeviceChannel deviceChannel);

    String createDeviceDirectExchange(DeviceChannel deviceChannel);

    void deleteDeviceChannel(DeviceChannel deviceChannel);

    void sendMessageToChannel(DeviceChannel deviceChannel, DeviceChannelMsg deviceChannelMsg) throws JsonProcessingException;
}
