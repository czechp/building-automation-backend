package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.query;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.dto.DeviceEventQueryDto;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortFindAll;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class DeviceEventQueryImpl implements DeviceEventQuery {
    private final DeviceEventPortFindAll deviceEventPortFindAll;

    @Override
    public List<DeviceEventQueryDto> findAllDeviceEvents(Pageable pageable) {
         return deviceEventPortFindAll.findAllDeviceEvents(pageable)
                .stream()
                .map(DeviceEventQueryDto::create)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeviceEventQueryDto> findByCurrentUser(Pageable pageable) {
    }

    @Override
    public DeviceEventQueryDto findById() {
        return null;
    }
}
