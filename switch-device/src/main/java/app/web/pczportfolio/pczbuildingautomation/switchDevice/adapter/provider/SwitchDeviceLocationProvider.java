package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.provider;

import app.web.pczportfolio.pczbuildingautomation.location.LocationFacade;
import app.web.pczportfolio.pczbuildingautomation.location.dto.LocationFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindLocationById;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindLocationByIdAndCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class SwitchDeviceLocationProvider implements SwitchDevicePortFindLocationByIdAndCurrentUser, SwitchDevicePortFindLocationById {
    private final LocationFacade locationFacade;

    @Override
    public Optional<LocationFacadeDto> findLocationByIdAndCurrentUser(long locationId) {
        return locationFacade.findLocationByCurrentUserAndId(locationId);
    }

    @Override
    public Optional<LocationFacadeDto> findLocationById(long locationId) {
        return locationFacade.findLocationById(locationId);
    }
}
