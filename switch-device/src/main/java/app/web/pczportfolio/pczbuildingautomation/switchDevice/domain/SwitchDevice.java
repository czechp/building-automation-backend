package app.web.pczportfolio.pczbuildingautomation.switchDevice.domain;

import app.web.pczportfolio.pczbuildingautomation.device.Device;
import app.web.pczportfolio.pczbuildingautomation.location.dto.LocationFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceCreateDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceFeedbackDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceSetStateDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(setterPrefix = "with")
public class SwitchDevice implements Device<SwitchDevice, SwitchDeviceSetStateDto, SwitchDeviceFeedbackDto> {
    private long id;

    private long version;

    private LocalDateTime creationTimestamp;

    private LocalDateTime lastStateUpdate;

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
                switchDeviceCreateDto.getName(),
                locationToAssign.getOwnerUsername(),
                false,
                false,
                false,
                new LocationParent(locationToAssign.getId(), locationToAssign.getName())
        );
    }

    @Override
    public SwitchDevice setState(SwitchDeviceSetStateDto setStateDto) {
        return null;
    }

    @Override
    public SwitchDevice receiveFeedback(SwitchDeviceFeedbackDto feedbackDto) {
        return null;
    }

    @Override
    public boolean checkDeviceError() {
        return false;
    }
}
