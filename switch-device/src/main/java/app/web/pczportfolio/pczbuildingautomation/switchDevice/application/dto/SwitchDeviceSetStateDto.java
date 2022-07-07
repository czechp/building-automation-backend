package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwitchDeviceSetStateDto {
    private long switchDeviceId;
    private boolean newState;
}
