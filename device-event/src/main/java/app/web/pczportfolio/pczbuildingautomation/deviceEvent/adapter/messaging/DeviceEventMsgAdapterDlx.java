package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.messaging;

import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.MessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
class DeviceEventMsgAdapterDlx {
    private static final String SWITCH_DEVICE_DLX_QUEUE = "q.switch-device.dlx";
    private static final String DEVICE_EVENT_LISTENER_ID = "device-event-dlx-consumer";

    private final MessagingService messagingService;

    public DeviceEventMsgAdapterDlx(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @RabbitListener(id = DEVICE_EVENT_LISTENER_ID, queues = {SWITCH_DEVICE_DLX_QUEUE}, autoStartup = "false")
    void consumeRejectedMsgFromDlx(MessageFromDeviceDlx deviceChannelMsg) {

    }

    @EventListener(ApplicationReadyEvent.class)
    void init() {
        messagingService.listenerActivation(DEVICE_EVENT_LISTENER_ID);
    }

}
