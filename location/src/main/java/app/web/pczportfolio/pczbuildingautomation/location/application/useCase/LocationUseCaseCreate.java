package app.web.pczportfolio.pczbuildingautomation.location.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationCreateCommandDto;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;

public interface LocationUseCaseCreate {
    Location createLocation(LocationCreateCommandDto locationCommandDto);
}
