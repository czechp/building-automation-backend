package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto;

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
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class LocationParentQueryDto {
    private long id;
    private String name;
}
