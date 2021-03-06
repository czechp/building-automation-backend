package app.web.pczportfolio.pczbuildingautomation.location.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationCreateCommandDto {
    @NotNull(message = "Location name cannot be null")
    @NotBlank(message = "Location name cannot be blank")
    private String name;

}
