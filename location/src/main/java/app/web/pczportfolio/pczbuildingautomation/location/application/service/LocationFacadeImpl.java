package app.web.pczportfolio.pczbuildingautomation.location.application.service;

import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityCurrentUser;
import app.web.pczportfolio.pczbuildingautomation.location.LocationFacade;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindById;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindByIdAndAccountUsername;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import app.web.pczportfolio.pczbuildingautomation.location.dto.LocationFacadeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class LocationFacadeImpl implements LocationFacade {
    private final LocationPortFindByIdAndAccountUsername locationPortFindByIdAndAccountUsername;
    private final LocationPortFindById locationPortFindById;
    private final SecurityCurrentUser securityCurrentUser;

    @Override
    public Optional<LocationFacadeDto> findLocationByCurrentUserAndId(long locationId) {
        final var currentAccountUsername = securityCurrentUser.getCurrentUser();
        return locationPortFindByIdAndAccountUsername
                .findLocationByIdAndAccountUsername(locationId, currentAccountUsername)
                .map(this::toFacadeDto);
    }

    @Override
    public Optional<LocationFacadeDto> findLocationById(long locationId) {
        return locationPortFindById.findLocationById(locationId)
                .map(this::toFacadeDto);
    }

    private LocationFacadeDto toFacadeDto(Location location) {
        return new LocationFacadeDto(
                location.getId(),
                location.getName(),
                location.getAccountParent().getUsername()
        );
    }
}
