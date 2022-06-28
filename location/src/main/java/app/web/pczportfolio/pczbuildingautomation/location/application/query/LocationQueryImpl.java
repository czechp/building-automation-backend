package app.web.pczportfolio.pczbuildingautomation.location.application.query;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityCurrentUser;
import app.web.pczportfolio.pczbuildingautomation.exception.NotEnoughPrivilegesException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationQueryDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortQuery;
import app.web.pczportfolio.pczbuildingautomation.location.application.service.LocationOwnerValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class LocationQueryImpl implements LocationQuery {
    private final LocationPortQuery locationPortQuery;
    private final LocationOwnerValidator locationOwnerValidator;
    private final SecurityCurrentUser securityCurrentUser;

    @Override
    public List<LocationQueryDto> findLocationsAll(Pageable pageable) {
        return locationPortQuery.findAllLocations(pageable);
    }

    @Override
    public List<LocationQueryDto> findLocationsByCurrentUser() {
        final var currentUser = securityCurrentUser.getCurrentUser();
        return locationPortQuery.findLocationsByAccountUsername(currentUser);
    }

    @Override
    public LocationQueryDto findLocationById(long locationId) {
        LocationQueryDto locationQueryDto = locationPortQuery.findLocationById(locationId)
                .orElseThrow(() -> new NotFoundException("There is no location with id: " + locationId));

        if (locationOwnerValidator.currentUserIsOwner(locationQueryDto))
            return locationQueryDto;
        else throw new NotEnoughPrivilegesException("You are not owner of location with id: " + locationId);
    }

    private boolean currentUserIsOwner(AccountFacadeDto currentUserAccount, LocationQueryDto location) {
        return currentUserAccount.getUsername().equals(location.getOwner());
    }


    private boolean currentUserIsAdmin(AccountFacadeDto currentUserAccount) {
        return currentUserAccount.getRole().equals("ADMIN");
    }


}
