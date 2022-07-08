package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortSave;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class DeviceEventPersistenceAdapter implements DeviceEventPortSave {
    private final DeviceEventJpaRepository deviceEventJpaRepository;

    @Override
    public DeviceEvent save(DeviceEvent deviceEvent) {
        final var deviceEventEntity = DeviceEventEntityMapper.toEntity(deviceEvent);
        return DeviceEventEntityMapper.toDomain(deviceEventJpaRepository.save(deviceEventEntity));
    }
}
