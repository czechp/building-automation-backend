package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.messaging;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortChannelCreate;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.MessagingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SwitchDeviceMsgAdapterChannelCreate implements SwitchDevicePortChannelCreate {
    private final MessagingService messagingService;

    @Override
    public void createChannelForSwitchDevice(SwitchDevice switchDevice) {
        messagingService.createDeviceChannel(switchDevice);
    }
}
