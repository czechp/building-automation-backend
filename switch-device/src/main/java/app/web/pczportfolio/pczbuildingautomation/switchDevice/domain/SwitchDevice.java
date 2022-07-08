package app.web.pczportfolio.pczbuildingautomation.switchDevice.domain;

import app.web.pczportfolio.pczbuildingautomation.device.Device;
import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiledException;
import app.web.pczportfolio.pczbuildingautomation.location.dto.LocationFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceCreateDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceFeedbackDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceSetNewStateDto;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannel;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(setterPrefix = "with")
public class SwitchDevice implements
        Device<SwitchDeviceSetNewStateDto, SwitchDeviceFeedbackDto>,
        DeviceChannel<Boolean> {
    private long id;

    private long version;

    private LocalDateTime creationTimestamp;

    private LocalDateTime lastFeedBackTimestamp;

    private LocalDateTime lastSetCommandTimestamp;

    private String name;

    private String owner;

    private boolean expectedState;

    private boolean state;

    private boolean deviceError;

    private LocationParent locationParent;

    public static SwitchDevice create(SwitchDeviceCreateDto switchDeviceCreateDto, LocationFacadeDto locationToAssign) {
        return new SwitchDevice(
                0L,
                0L,
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                switchDeviceCreateDto.getName(),
                locationToAssign.getOwnerUsername(),
                false,
                false,
                false,
                new LocationParent(locationToAssign.getId(), locationToAssign.getName())
        );
    }


    @Override
    public void setNewState(SwitchDeviceSetNewStateDto setStateDto) {
        this.expectedState = setStateDto.isNewState();
        this.lastSetCommandTimestamp = LocalDateTime.now();
    }

    @Override
    public void receiveFeedback(SwitchDeviceFeedbackDto feedbackDto) {
        stateFromFeedbackMatchToExpectedState(feedbackDto);
        this.deviceError = false;
        this.lastFeedBackTimestamp = LocalDateTime.now();
        this.state = this.expectedState;
    }

    private void stateFromFeedbackMatchToExpectedState(SwitchDeviceFeedbackDto switchDeviceFeedbackDto) {
        if (switchDeviceFeedbackDto.isNewState() != this.expectedState)
            throw new ConditionsNotFulFiledException("State from feedback does not match to feedback value");
    }

    @Override
    public void checkDeviceError() {
        if (!expectedStateAndStateEquals())
            this.deviceError = examineTimeBetweenLastStateUpdate();

    }

    private boolean expectedStateAndStateEquals() {
        return this.expectedState == this.state;
    }

    private boolean examineTimeBetweenLastStateUpdate() {
        final long minutesSinceLastUpdate = Duration.between(this.lastSetCommandTimestamp, LocalDateTime.now()).toMinutes();
        return minutesSinceLastUpdate > SwitchDeviceConfiguration.MINUTES_TO_DEVICE_ERROR;
    }

    @Override
    public String getChannelRootName() {
        return SwitchDeviceConfiguration.SWITCH_DEVICE_QUEUE_NAME;
    }

    @Override
    public Boolean getState() {
        return this.state;
    }

    @Override
    public Boolean getExpectedState() {
        return this.expectedState;
    }

    @Override
    public String getDeviceTypeName() {
        return SwitchDeviceConfiguration.SWITCH_DEVICE_NAME;
    }
}
