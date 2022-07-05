package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.messaging;

import app.web.pczportfolio.pczbuildingautomation.configuration.messaging.MessagingService;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class SwitchDeviceExchangeInitializer {
    private final MessagingService messagingService;
    private final Logger logger;

    public SwitchDeviceExchangeInitializer(MessagingService messagingService) {
        this.messagingService = messagingService;
        this.logger = LoggerFactory.getLogger(SwitchDeviceExchangeInitializer.class);
    }

    @EventListener(ApplicationReadyEvent.class)
    void createExchangeForSwitchDevice() {
        SwitchDevice initialSwitchDevice = new SwitchDevice();
        final var nameOfExchangeForSwitchDevice = messagingService.createDeviceDirectExchange(initialSwitchDevice);
        logger.info("<>--------------------------Exchange for SWITCH DEVICE created--------------------------<>");


    }
}
