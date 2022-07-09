package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.aop;

import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

class DeviceEventAopDeviceChannelMapper {
    private final Logger logger = LoggerFactory.getLogger(DeviceEventAopDeviceChannelMapper.class);

    static Optional<DeviceChannel> castToDeviceChannel(Object object) {
        return object instanceof DeviceChannel ? Optional.of((DeviceChannel) object) : Optional.empty();
    }
}
