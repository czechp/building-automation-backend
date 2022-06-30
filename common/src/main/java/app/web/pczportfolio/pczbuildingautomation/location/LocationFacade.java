package app.web.pczportfolio.pczbuildingautomation.location;

import app.web.pczportfolio.pczbuildingautomation.location.dto.LocationFacadeDto;

import java.util.Optional;

public interface LocationFacade {
    Optional<LocationFacadeDto> findLocationByCurrentUserAndId(long locationId);
}
