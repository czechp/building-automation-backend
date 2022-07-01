package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.query;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceQueryDto;

import java.util.List;
import java.util.Optional;

public interface SwitchDeviceQuery {
    Optional<SwitchDeviceQueryDto> findSwitchDeviceById(long switchDeviceId);

    List<SwitchDeviceQueryDto> findAllSwitchDevices();

    List<SwitchDeviceQueryDto> findAllSwitchDevicesByCurrentUser();
}
