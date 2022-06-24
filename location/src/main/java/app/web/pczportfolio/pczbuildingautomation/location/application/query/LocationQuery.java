package app.web.pczportfolio.pczbuildingautomation.location.application.query;

import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationQueryDto;

import java.util.List;
import java.util.Optional;

public interface LocationQuery {
    List<LocationQueryDto> findLocationsAll();

    Optional<LocationQueryDto> findLocationById(long locationId);

    List<LocationQueryDto> findLocationsByCurrentUser();
}
