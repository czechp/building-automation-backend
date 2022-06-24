package app.web.pczportfolio.pczbuildingautomation.location.application.query;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityCurrentUser;
import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiledException;
import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationQueryDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindAccountUsername;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindAll;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindByAccountId;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindById;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class LocationQueryImpl implements LocationQuery {
    private final LocationPortFindAll locationPortFindAll;
    private final LocationPortFindByAccountId locationPortFindByAccountId;
    private final SecurityCurrentUser securityCurrentUser;
    private final LocationPortFindAccountUsername locationPortFindAccountUsername;
    private final LocationPortFindById locationPortFindById;

    @Override
    public List<LocationQueryDto> findLocationsAll() {
        return locationPortFindAll.findLocationsAll()
                .stream()
                .map(LocationQueryDto::toQueryDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<LocationQueryDto> findLocationsByCurrentUser() {
        String currentUser = securityCurrentUser.getCurrentUser();
        AccountFacadeDto currentUserAccount = locationPortFindAccountUsername.findAccountByUsername(currentUser)
                .orElseThrow(() -> new ConditionsNotFulFiledException("This user has no account."));
        return locationPortFindByAccountId.findLocationsByAccountId(currentUserAccount.getId())
                .stream()
                .map(LocationQueryDto::toQueryDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LocationQueryDto> findLocationById(long locationId) {
        return locationPortFindById.findLocationById(locationId)
                .map(LocationQueryDto::toQueryDto);
    }

}
