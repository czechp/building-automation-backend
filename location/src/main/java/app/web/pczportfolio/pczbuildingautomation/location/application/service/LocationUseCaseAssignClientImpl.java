package app.web.pczportfolio.pczbuildingautomation.location.application.service;

import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiledException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotEnoughPrivilegesException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortExistsByClientUUID;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindById;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortSave;
import app.web.pczportfolio.pczbuildingautomation.location.application.useCase.LocationUseCaseAssignClient;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class LocationUseCaseAssignClientImpl implements LocationUseCaseAssignClient {
    private final LocationPortFindById locationPortFindById;
    private final LocationOwnerValidator locationOwnerValidator;
    private final LocationPortExistsByClientUUID locationPortExistsByClientUUID;
    private final LocationPortSave locationPortSave;


    @Override
    @Transactional
    public Location assignClient(long locationId, String clientName, String clientUUID) {
        if (locationPortExistsByClientUUID.locationExistsWithClientUUID(clientUUID))
            throw new ConditionsNotFulFiledException("This client is already assigned to location");
        Location location = locationPortFindById.findLocationById(locationId)
                .orElseThrow(() -> new NotFoundException("Location with id: " + locationId + " does not exist."));
        if (!locationOwnerValidator.currentUserIsOwner(location))
            throw new NotEnoughPrivilegesException("You are not owner of location");
        location.assignClient(clientUUID, clientName);
        locationPortSave.saveLocation(location);
        return location;
    }


    @Override
    @Transactional
    public Location clearClient(long locationId) {
        Location location = locationPortFindById.findLocationById(locationId)
                .orElseThrow(() -> new NotFoundException("Location with id: " + locationId + " does not exists"));
        if(!locationOwnerValidator.currentUserIsOwner(location))
            throw  new NotEnoughPrivilegesException("You are not owner of location");
        location.clearClientBinding();
        locationPortSave.saveLocation(location);
        return location;
    }
}
