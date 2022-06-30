package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.provider;

import app.web.pczportfolio.pczbuildingautomation.location.LocationFacade;
import app.web.pczportfolio.pczbuildingautomation.location.dto.LocationFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindLocationByIdAndCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class LocationProvider implements SwitchDevicePortFindLocationByIdAndCurrentUser {
    private final LocationFacade locationFacade;

    @Override
    public Optional<LocationFacadeDto> findLocationByIdAndCurrentUser(long locationId) {
        return locationFacade.findLocationByCurrentUserAndId(locationId);
    }
}
