package app.web.pczportfolio.pczbuildingautomation.location.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationCreateCommandDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindAccountById;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortSave;
import app.web.pczportfolio.pczbuildingautomation.location.application.useCase.LocationUseCaseCreate;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class LocationUseCaseCreateImpl implements LocationUseCaseCreate {
    private final LocationPortSave locationPortSave;
    private final LocationPortFindAccountById locationPortFindAccountById;

    @Override
    public Location createLocation(LocationCreateCommandDto locationCommandDto) {
        AccountFacadeDto accountFacadeDto = locationPortFindAccountById.findAccountById(locationCommandDto.getAccountId())
                .orElseThrow(() -> new NotFoundException("There is no account with id: " + locationCommandDto.getAccountId()));
        Location location = Location.create(locationCommandDto, accountFacadeDto);
        locationPortSave.saveLocation(location);
        return location;
    }
}
