package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.query;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceQueryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SwitchDeviceQuery {
    SwitchDeviceQueryDto findSwitchDeviceById(long switchDeviceId);

    List<SwitchDeviceQueryDto> findAllSwitchDevices(Pageable pageable);

    List<SwitchDeviceQueryDto> findAllSwitchDevicesByCurrentUser(Pageable pageable);

    List<SwitchDeviceQueryDto> findByLocationIdAndCurrentUser(long locationId, Pageable pageable);
}
