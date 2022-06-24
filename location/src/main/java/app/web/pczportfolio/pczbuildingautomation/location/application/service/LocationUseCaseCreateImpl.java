package app.web.pczportfolio.pczbuildingautomation.location.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityCurrentUser;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationCreateCommandDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindAccountUsername;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortSave;
import app.web.pczportfolio.pczbuildingautomation.location.application.useCase.LocationUseCaseCreate;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class LocationUseCaseCreateImpl implements LocationUseCaseCreate {
    private final LocationPortSave locationPortSave;
    private final LocationPortFindAccountUsername locationPortFindAccountUsername;
    private final SecurityCurrentUser securityCurrentUser;

    @Override
    public Location createLocation(LocationCreateCommandDto locationCommandDto) {
        String currentUser = securityCurrentUser.getCurrentUser();
        AccountFacadeDto accountFacadeDto = locationPortFindAccountUsername.findAccountByUsername(currentUser)
                .orElseThrow(() -> new NotFoundException("There is no active logged user"));
        Location location = Location.create(locationCommandDto, accountFacadeDto);
        locationPortSave.saveLocation(location);
        return location;
    }
}
