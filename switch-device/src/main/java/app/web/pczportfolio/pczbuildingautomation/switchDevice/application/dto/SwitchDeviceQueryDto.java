package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwitchDeviceQueryDto {
    private long id;
    private LocalDateTime creationTimestamp;
    private String name;
    private String owner;
    private boolean expectedState;
    private boolean state;
    private boolean deviceError;
    private LocationParentQueryDto location;

    public static SwitchDeviceQueryDto toQueryDto(SwitchDevice switchDevice) {
        return new SwitchDeviceQueryDto(
                switchDevice.getId(),
                switchDevice.getCreationTimestamp(),
                switchDevice.getName(),
                switchDevice.getOwner(),
                switchDevice.isExpectedState(),
                switchDevice.isState(),
                switchDevice.isDeviceError(),
                new LocationParentQueryDto(
                        switchDevice.getLocationParent().getId(),
                        switchDevice.getLocationParent().getName()
                )
        );
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class LocationParentQueryDto {
    private long id;
    private String name;
}
