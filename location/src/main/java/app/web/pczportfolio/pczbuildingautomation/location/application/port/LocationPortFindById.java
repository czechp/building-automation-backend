package app.web.pczportfolio.pczbuildingautomation.location.application.port;

import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;

import java.util.Optional;

public interface LocationPortFindById {
    Optional<Location> findLocationById(long locationId);
}
