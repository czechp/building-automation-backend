package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.messaging;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.useCase.DeviceEventUseCaseRejectedMsg;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.MessagingService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class DeviceEventMsgAdapterDlx {
    private static final String SWITCH_DEVICE_DLX_QUEUE = "q.switch-device.dlx";
    private static final String DEVICE_EVENT_LISTENER_ID = "device-event-dlx-consumer";

    private final MessagingService messagingService;
    private final DeviceEventUseCaseRejectedMsg deviceEventUseCaseRejectedMsg;

    @RabbitListener(id = DEVICE_EVENT_LISTENER_ID, queues = {SWITCH_DEVICE_DLX_QUEUE}, autoStartup = "false")
    void consumeRejectedMsgFromDlx(MessageFromDeviceDlx deviceChannelMsg) {
        deviceEventUseCaseRejectedMsg.createEventFromRejectedMsg(deviceChannelMsg);
    }

    @EventListener(ApplicationReadyEvent.class)
    void init() {
        messagingService.listenerActivation(DEVICE_EVENT_LISTENER_ID);
    }

}
