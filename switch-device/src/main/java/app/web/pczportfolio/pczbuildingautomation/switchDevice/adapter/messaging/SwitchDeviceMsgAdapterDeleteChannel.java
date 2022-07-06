package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.messaging;

import app.web.pczportfolio.pczbuildingautomation.configuration.messaging.MessagingService;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortDeleteChannel;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SwitchDeviceMsgAdapterDeleteChannel implements SwitchDevicePortDeleteChannel {
    private final MessagingService messagingService;

    @Override
    public void deleteSwitchDeviceChannel(SwitchDevice switchDevice) {
        messagingService.deleteDeviceChannel(switchDevice);
    }
}
