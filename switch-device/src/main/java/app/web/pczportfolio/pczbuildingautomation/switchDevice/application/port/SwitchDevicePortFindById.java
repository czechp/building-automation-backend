package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;

import java.util.Optional;

public interface SwitchDevicePortFindById {
    Optional<SwitchDevice> findSwitchDeviceById(long switchDeviceId);
}
