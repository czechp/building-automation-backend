package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.query;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceQueryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SwitchDeviceQuery {
    SwitchDeviceQueryDto findSwitchDeviceById(long switchDeviceId);

    List<SwitchDeviceQueryDto> findAllSwitchDevices(Pageable pageable);

    List<SwitchDeviceQueryDto> findAllSwitchDevicesByCurrentUser(Pageable pageable);
}
