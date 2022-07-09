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
    }

    public static DeviceEvent createEvent(DeviceChannel deviceChannel, EventType eventType, boolean failed) {
        final var deviceEvent = new DeviceEvent(deviceChannel);
        deviceEvent.eventType = eventType;
        deviceEvent.failed = failed;
        return deviceEvent;
    }

    public static DeviceEvent deleteEvent(DeviceChannel deviceChannel) {
        final var deleteDeviceEvent = new DeviceEvent(deviceChannel);
        deleteDeviceEvent.eventType = EventType.DELETE;
        deleteDeviceEvent.content = "Deleted the device: " + deleteDeviceEvent.getDeviceType() + " with name: " + deleteDeviceEvent.getDeviceName();
        return deleteDeviceEvent;
    }

    public static DeviceEvent newStateRequestEvent(DeviceChannel deviceChannel) {
        final var newStateRequestDeviceEvent = new DeviceEvent(deviceChannel);
        newStateRequestDeviceEvent.eventType = EventType.NEW_STATE_REQUEST;
        final var expectedState = recognizeStateType(deviceChannel.getDeviceExpectedState());
        newStateRequestDeviceEvent.content = newStateRequestDeviceEvent.getDeviceName() + " request for new state." +
                " Expected state: " + expectedState;
        return newStateRequestDeviceEvent;
    }

    public static DeviceEvent feedbackFromDeviceEvent(DeviceChannel deviceChannel) {
        final var feedbackFromDeviceEvent = new DeviceEvent(deviceChannel);
        feedbackFromDeviceEvent.eventType = EventType.FEEDBACK_FROM_DEVICE;
        final var currentState = recognizeStateType(deviceChannel.getDeviceState());
        feedbackFromDeviceEvent.content = feedbackFromDeviceEvent.getDeviceName() + " feedback from device." +
                " Current state: " + currentState;
        return feedbackFromDeviceEvent;
    }

    private static String recognizeStateType(Object deviceState) {
        if (deviceState instanceof Boolean) {
            return (boolean) deviceState ? "ON" : "OFF";
        }

        return "UNRECOGNIZED";
    }
}
