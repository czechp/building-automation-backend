package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.messaging;

import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannelMsg;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeviceEventMsgAdapterDlx {
    private static final String SWITCH_DEVICE_DLX_QUEUE = "q.switch-device.dlx";
    private static final String DEVICE_EVENT_LISTENER_NAME = "device-event-dlx-consumer";
    private final RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    @RabbitListener(id = DEVICE_EVENT_LISTENER_NAME, queues = {SWITCH_DEVICE_DLX_QUEUE}, autoStartup = "false")
    void consumeRejectedMsgFromDlx(MessageFromDeviceDlx deviceChannelMsg) {
        System.out.println(deviceChannelMsg.getDeviceId());
        System.out.println(deviceChannelMsg.getDeviceName());
    }


    @NoArgsConstructor
    private static class MessageFromDeviceDlx implements DeviceChannelMsg {
        private long deviceId;
        private String newState;
        private String owner;
        private String deviceName;
        private String deviceType;

        @Override
        public long getDeviceId() {
            return deviceId;
        }

        @Override
        public String getNewState() {
            return newState;
        }

        @Override
        public String getOwner() {
            return owner;
        }

        @Override
        public String getDeviceName() {
            return deviceName;
        }

        @Override
        public String getDeviceType() {
            return deviceType;
        }
    }
}
