package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortSave;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SwitchDevicePersistenceAdapter implements SwitchDevicePortSave {
    private final SwitchDeviceJpaRepository switchDeviceJpaRepository;

    @Override
    public SwitchDevice saveSwitchDevice(SwitchDevice switchDevice) {
        final var switchDeviceToSave = SwitchDeviceEntityMapper.toEntity(switchDevice);
        return SwitchDeviceEntityMapper.toDomain(switchDeviceJpaRepository.save(switchDeviceToSave));
    }
}
