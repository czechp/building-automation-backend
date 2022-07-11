package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortFindAll;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortFindByOwner;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortSave;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class DeviceEventPersistenceAdapter implements
        DeviceEventPortSave,
        DeviceEventPortFindAll,
        DeviceEventPortFindByOwner {
    private final DeviceEventJpaRepository deviceEventJpaRepository;

    @Override
    public DeviceEvent save(DeviceEvent deviceEvent) {
        final var deviceEventEntity = DeviceEventEntityMapper.toEntity(deviceEvent);
        return DeviceEventEntityMapper.toDomain(deviceEventJpaRepository.save(deviceEventEntity));
    }

    @Override
    public List<DeviceEvent> findAllDeviceEvents(Pageable pageable) {
        return deviceEventJpaRepository.findAll()
                .stream()
                .map(DeviceEventEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeviceEvent> findDeviceEventsByOwner(String owner, Pageable pageable) {
        return deviceEventJpaRepository.findByOwner(owner, pageable)
                .stream()
                .map(DeviceEventEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
