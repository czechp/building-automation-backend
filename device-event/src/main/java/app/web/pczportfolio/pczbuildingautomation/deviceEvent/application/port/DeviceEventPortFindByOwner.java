package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeviceEventPortFindByOwner {
    List<DeviceEvent> findDeviceEventsByOwner(String owner, Pageable pageable);
}
