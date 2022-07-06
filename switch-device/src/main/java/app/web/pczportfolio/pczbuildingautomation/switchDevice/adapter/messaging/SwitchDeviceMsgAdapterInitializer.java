package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.messaging;

import app.web.pczportfolio.pczbuildingautomation.configuration.messaging.MessagingChannel;
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
        removeAllSwitchDeviceQueue();
        createExchangeForSwitchDevice();
        createChannelForDevices();
        LoggerInfo.showInfo(logger, "Initializing messaging structure for SWITCH DEVICE finished ");

    }

    private void removeAllSwitchDeviceQueue() {
        switchDevicePortFindAll.findAllSwitchDevices(Pageable.unpaged())
                .forEach(messagingService::deleteDeviceChannel);
    }

    private void createExchangeForSwitchDevice() {
        SwitchDevice initialSwitchDevice = new SwitchDevice();
        final var nameOfExchangeForSwitchDevice = messagingService.createDeviceDirectExchange(initialSwitchDevice);
    }


    private void createChannelForDevices() {
        MessagingChannel messagingChannel = new MessagingChannel() {
            @Override
            public long getId() {
                return 111;
            }

            @Override
            public String getOwner() {
                return "someOwner";
            }

            @Override
            public String getChannelRootName() {
                return "switch-device";
            }
        };

        messagingService.createDeviceQueue(messagingChannel);
    }
}
