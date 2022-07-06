package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.messaging;

import app.web.pczportfolio.pczbuildingautomation.configuration.messaging.MessagingService;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindAll;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import app.web.pczportfolio.pczbuildingautomation.utilities.LoggerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SwitchDeviceMsgAdapterInitializer {
    private final MessagingService messagingService;
    private final SwitchDevicePortFindAll switchDevicePortFindAll;
    private final Logger logger;

    public SwitchDeviceMsgAdapterInitializer(MessagingService messagingService, SwitchDevicePortFindAll switchDevicePortFindAll) {
        this.messagingService = messagingService;
        this.switchDevicePortFindAll = switchDevicePortFindAll;
        this.logger = LoggerFactory.getLogger(SwitchDeviceMsgAdapterInitializer.class);
    }

    @EventListener(ApplicationReadyEvent.class)
    void initializingSwitchDeviceMessaging() {
        LoggerInfo.showInfo(logger, "Initializing messaging structure for SWITCH DEVICE started ");
        createExchangeForSwitchDevice();
        createChannelForDevices();
        LoggerInfo.showInfo(logger, "Initializing messaging structure for SWITCH DEVICE finished ");

    }

    private void createExchangeForSwitchDevice() {
        SwitchDevice initialSwitchDevice = new SwitchDevice();
        final var nameOfExchangeForSwitchDevice = messagingService.createDeviceDirectExchange(initialSwitchDevice);
    }


    private void createChannelForDevices() {
        switchDevicePortFindAll
                .findAllSwitchDevices(Pageable.unpaged())
                .forEach(messagingService::createDeviceChannel);
    }
}
