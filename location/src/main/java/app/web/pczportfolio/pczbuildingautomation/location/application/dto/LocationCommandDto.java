package app.web.pczportfolio.pczbuildingautomation.location.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationCommandDto {
    @NotNull(message = "Account id cannot be null")
    private long accountId;
    @NotNull(message = "Location name cannot be null")
    @NotBlank(message = "Location name cannot be blank")
    private String name;

}
