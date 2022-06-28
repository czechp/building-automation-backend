package app.web.pczportfolio.pczbuildingautomation.location.application.service;

import app.web.pczportfolio.pczbuildingautomation.exception.NotEnoughPrivilegesException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortDelete;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindById;
import app.web.pczportfolio.pczbuildingautomation.location.application.useCase.LocationUseCaseDelete;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class LocationUseCaseDeleteImpl implements LocationUseCaseDelete {
    private final LocationPortFindById locationPortFindById;
    private final LocationPortDelete locationPortDelete;
    private final LocationOwnerValidator locationOwnerValidator;

    @Override
    public void deleteLocation(long locationId) {
        Location location = locationPortFindById.findLocationById(locationId)
                .orElseThrow(() -> new NotFoundException("Location with id: " + locationId + " does not exist"));
        if (locationOwnerValidator.currentUserIsOwner(location))
            locationPortDelete.deleteLocation(location);
        else throw new NotEnoughPrivilegesException("You are not owner of this location");
    }
}
