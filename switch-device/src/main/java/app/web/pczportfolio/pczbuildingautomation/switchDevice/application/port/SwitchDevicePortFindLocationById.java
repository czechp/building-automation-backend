package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port;

import app.web.pczportfolio.pczbuildingautomation.location.dto.LocationFacadeDto;

import java.util.Optional;

public interface SwitchDevicePortFindLocationById {
    Optional<LocationFacadeDto> findLocationById(long locationId);
}
