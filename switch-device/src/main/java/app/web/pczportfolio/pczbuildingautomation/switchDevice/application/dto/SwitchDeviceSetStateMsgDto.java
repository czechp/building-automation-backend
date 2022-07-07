package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SwitchDeviceSetStateMsgDto {
    private boolean newState;
    private LocalDateTime creationTimestamp;

    public SwitchDeviceSetStateMsgDto(boolean newState) {
        this.newState = newState;
        this.creationTimestamp = LocalDateTime.now();
    }
}
