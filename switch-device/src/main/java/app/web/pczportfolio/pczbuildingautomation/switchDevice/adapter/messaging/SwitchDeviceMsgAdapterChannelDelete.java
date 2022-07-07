package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.messaging;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortChannelDelete;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.MessagingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SwitchDeviceMsgAdapterChannelDelete implements SwitchDevicePortChannelDelete {
    private final MessagingService messagingService;

    @Override
    public void deleteSwitchDeviceChannel(SwitchDevice switchDevice) {
        messagingService.deleteDeviceChannel(switchDevice);
    }
}
