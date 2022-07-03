package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwitchDeviceFeedbackDto {
    @NotNull(message = "Id cannot be null")
    private long id;
    @NotNull(message = "New state cannot be null")
    private boolean newState;
}
