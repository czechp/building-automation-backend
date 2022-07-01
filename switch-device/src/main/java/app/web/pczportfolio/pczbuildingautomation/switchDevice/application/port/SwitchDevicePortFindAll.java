package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SwitchDevicePortFindAll {
    List<SwitchDevice> findAllSwitchDevices(Pageable pageable);
}
