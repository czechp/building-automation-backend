package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.query;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.dto.DeviceEventQueryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeviceEventQuery {
    List<DeviceEventQueryDto> findAllDeviceEvents(Pageable pageable);
    List<DeviceEventQueryDto> findByCurrentUser(String owner, Pageable pageable);
    DeviceEventQueryDto findById();
}
