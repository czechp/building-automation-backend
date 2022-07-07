package app.web.pczportfolio.pczbuildingautomation.location.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationCreateCommandDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindCurrentUserAccount;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortSave;
import app.web.pczportfolio.pczbuildingautomation.location.application.useCase.LocationUseCaseCreate;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class LocationUseCaseCreateImpl implements LocationUseCaseCreate {
    private final LocationPortSave locationPortSave;
    private final LocationPortFindCurrentUserAccount locationPortFindCurrentUserAccount;

    @Override
    public Location createLocation(LocationCreateCommandDto locationCommandDto) {
        AccountFacadeDto accountFacadeDto = locationPortFindCurrentUserAccount.findAccountOfCurrentUser()
                .orElseThrow(() -> new NotFoundException("There is no active logged user"));
        Location location = Location.create(locationCommandDto, accountFacadeDto);
        locationPortSave.saveLocation(location);
        return location;
    }
}
