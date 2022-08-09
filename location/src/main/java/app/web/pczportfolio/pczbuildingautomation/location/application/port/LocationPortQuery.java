package app.web.pczportfolio.pczbuildingautomation.location.application.port;

import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationQueryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LocationPortQuery {
    List<LocationQueryDto> findAllLocations(Pageable pageable);

    List<LocationQueryDto> findLocationsByAccountUsername(String username, Pageable pageable);

    Optional<LocationQueryDto> findLocationById(long locationId);
}
