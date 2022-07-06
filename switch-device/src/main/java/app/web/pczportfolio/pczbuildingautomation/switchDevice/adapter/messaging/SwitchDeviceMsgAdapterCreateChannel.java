package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.messaging;

import app.web.pczportfolio.pczbuildingautomation.configuration.messaging.MessagingService;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortCreateChannel;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SwitchDeviceMsgAdapterCreateChannel implements SwitchDevicePortCreateChannel {
    private final MessagingService messagingService;

    @Override
    public void createChannelForSwitchDevice(SwitchDevice switchDevice) {
        messagingService.createDeviceChannel(switchDevice);
    }
}
