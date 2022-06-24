package app.web.pczportfolio.pczbuildingautomation.location.application.query;

import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationQueryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LocationQuery {
    List<LocationQueryDto> findLocationsAll(Pageable pageable);

    LocationQueryDto findLocationById(long locationId);

    List<LocationQueryDto> findLocationsByCurrentUser();

}
