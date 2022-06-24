package app.web.pczportfolio.pczbuildingautomation.location.application.query;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityCurrentUser;
import app.web.pczportfolio.pczbuildingautomation.exception.NotEnoughPrivilegesException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationQueryDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindAccountByUsername;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class LocationQueryImpl implements LocationQuery {
    private final LocationPortQuery locationPortQuery;
    private final SecurityCurrentUser securityCurrentUser;
    private final LocationPortFindAccountByUsername locationPortFindAccountByUsername;

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
        final var currentUser = securityCurrentUser.getCurrentUser();
        final var currentUserAccount = locationPortFindAccountByUsername.findAccountByUsername(currentUser)
                .orElseThrow(() -> new NotFoundException("There is no account with username: " + currentUser));
        final var location = locationPortQuery.findLocationById(locationId)
                .orElseThrow(() -> new NotFoundException("Location with id: " + locationId + " does not exist"));

        if (currentUserIsAdmin(currentUserAccount) || currentUserIsOwner(currentUserAccount, location))
            return location;
        else throw new NotEnoughPrivilegesException("You are not owner of location or admin");
    }

    private boolean currentUserIsOwner(AccountFacadeDto currentUserAccount, LocationQueryDto location) {
        return currentUserAccount.getUsername().equals(location.getOwner());
    }


    private boolean currentUserIsAdmin(AccountFacadeDto currentUserAccount) {
        return currentUserAccount.getRole().equals("ADMIN");
    }


}
