package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;

import java.util.List;

public interface DeviceEventPortFindByOwnerAndDeviceId {
    List<DeviceEvent> findDeviceEventsByOwnerAndDeviceId(String owner, long deviceId);
}
