package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwitchDeviceCreateDto {
    @NotNull(message = "Location id cannot be null")
    private long locationId;
    @NotNull(message = "Name of switch device cannot be null")
    @Length(min = 2, max = 200, message = "Name of switch device has to have length between 2 and 200 characters")
    private String name;
}
