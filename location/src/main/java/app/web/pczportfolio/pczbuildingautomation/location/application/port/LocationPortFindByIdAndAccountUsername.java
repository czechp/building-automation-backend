package app.web.pczportfolio.pczbuildingautomation.location.application.port;

import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;

import java.util.Optional;

public interface LocationPortFindByIdAndAccountUsername {
    Optional<Location> findLocationByIdAndAccountUsername(long locationId, String username);
}
