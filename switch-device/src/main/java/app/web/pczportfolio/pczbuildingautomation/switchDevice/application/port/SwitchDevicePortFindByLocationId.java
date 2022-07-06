package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;

import java.util.List;

public interface SwitchDevicePortFindByLocationId {
    List<SwitchDevice> findSwitchDevicesByLocationId(long locationId);
}
