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
    private DeviceEventType deviceEventType;

    private DeviceEvent(DeviceChannel deviceChannel) {
        this.deviceId = deviceChannel.getId();
        this.deviceName = deviceChannel.getName();
        this.deviceType = deviceChannel.getDeviceTypeName();
        this.owner = deviceChannel.getOwner();
        this.expectedState = deviceChannel.getEventExpectState();
        this.state = deviceChannel.getEventState();
    }

    public static DeviceEvent createEvent(DeviceChannel deviceChannel, DeviceEventType deviceEventType) {
        final var deviceEvent = new DeviceEvent(deviceChannel);
        deviceEvent.deviceEventType = deviceEventType;
        return deviceEvent;
    }


    public static DeviceEvent createFailedEvent(String user, DeviceEventType deviceEventType) {
        final var deviceEvent = new DeviceEvent();
        deviceEvent.owner = user;
        deviceEvent.deviceEventType = deviceEventType;
        deviceEvent.failed = true;
        return deviceEvent;
    }

}
