package app.web.pczportfolio.pczbuildingautomation.location.application.dto;

import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public
class LocationQueryDto {
    private long id;
    private String owner;
    private String name;

    public static LocationQueryDto toQueryDto(Location location) {
        return new LocationQueryDto(
                location.getId(),
                location.getAccountParent().getUsername(),
                location.getName()
        );
    }
}
