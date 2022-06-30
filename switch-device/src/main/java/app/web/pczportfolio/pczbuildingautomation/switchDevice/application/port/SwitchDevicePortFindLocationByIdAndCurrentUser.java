package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port;

import app.web.pczportfolio.pczbuildingautomation.location.dto.LocationFacadeDto;

import java.util.Optional;

public interface SwitchDevicePortFindLocationByIdAndCurrentUser {
    Optional<LocationFacadeDto> findLocationByIdAndCurrentUser(long locationId);
}
