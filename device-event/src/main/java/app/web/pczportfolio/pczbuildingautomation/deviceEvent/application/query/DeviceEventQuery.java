package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.query;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.dto.DeviceEventQueryDto;

import java.awt.print.Pageable;
import java.util.List;

public interface DeviceEventQuery {
    List<DeviceEventQueryDto> findAllDeviceEvents(Pageable pageable);
    List<DeviceEventQueryDto> findByCurrentUser(Pageable pageable);
    DeviceEventQueryDto findById();
}
