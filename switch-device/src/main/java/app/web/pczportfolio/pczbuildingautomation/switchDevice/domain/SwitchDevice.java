package app.web.pczportfolio.pczbuildingautomation.switchDevice.domain;

import app.web.pczportfolio.pczbuildingautomation.location.dto.LocationFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceCreateDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(setterPrefix = "with")
public class SwitchDevice {
    private long id;

    private long version;

    private LocalDateTime creationTimestamp;

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
                switchDeviceCreateDto.getName(),
                locationToAssign.getOwnerUsername(),
                false,
                false,
                false,
                new LocationParent(locationToAssign.getId(), locationToAssign.getName())
        );
    }
}
