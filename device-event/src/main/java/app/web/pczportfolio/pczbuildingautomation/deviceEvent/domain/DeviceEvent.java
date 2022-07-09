package app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain;

import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannel;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@EqualsAndHashCode
public
class DeviceEvent {
    private long id;
    private long version;
    private LocalDateTime creationTimestamp;
    private long deviceId;
    private String deviceName;
    private String deviceType;
    private String owner;
    private String expectedState;
    private String state;
    private boolean failed;
    private EventType eventType;

    private DeviceEvent(DeviceChannel deviceChannel) {
        this.deviceId = deviceChannel.getId();
        this.deviceName = deviceChannel.getName();
        this.deviceType = deviceChannel.getDeviceTypeName();
        this.owner = deviceChannel.getOwner();
        this.expectedState = deviceChannel.getEventExpectState();
        this.state = deviceChannel.getEventState();
    }

    public static DeviceEvent createEvent(DeviceChannel deviceChannel, EventType eventType) {
        final var deviceEvent = new DeviceEvent(deviceChannel);
        deviceEvent.eventType = eventType;
        return deviceEvent;
    }


    public static DeviceEvent createFailedEvent(String user, EventType eventType) {
        final var deviceEvent = new DeviceEvent();
        deviceEvent.owner = user;
        deviceEvent.eventType = eventType;
        deviceEvent.failed = true;
        return deviceEvent;
    }

    private static String recognizeStateType(Object deviceState) {
        if (deviceState instanceof Boolean) {
            return (boolean) deviceState ? "ON" : "OFF";
        }

        return "UNRECOGNIZED";
    }
}
