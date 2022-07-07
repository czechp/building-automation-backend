package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SwitchDeviceSetNewStateMsgDto {
    private boolean newState;
    private LocalDateTime creationTimestamp;

    public SwitchDeviceSetNewStateMsgDto(boolean newState) {
        this.newState = newState;
        this.creationTimestamp = LocalDateTime.now();
    }
}
