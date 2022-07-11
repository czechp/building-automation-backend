package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;

import java.util.Optional;

public interface DeviceEventPortFindById {
    Optional<DeviceEvent> findDeviceEventById(long id);
}
