package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.*;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class SwitchDevicePersistenceAdapter implements SwitchDevicePortSave,
        SwitchDevicePortFindAll,
        SwitchDevicePortFindByOwner,
        SwitchDevicePortFindById,
        SwitchDevicePortDelete,
        SwitchDevicePortFindByLocationId,
        SwitchDevicePortFindByLocationIdAndOwner {
    private final SwitchDeviceJpaRepository switchDeviceJpaRepository;

    @Override
    public SwitchDevice saveSwitchDevice(SwitchDevice switchDevice) {
        final var switchDeviceToSave = SwitchDeviceEntityMapper.toEntity(switchDevice);
        return SwitchDeviceEntityMapper.toDomain(switchDeviceJpaRepository.save(switchDeviceToSave));
    }

    @Override
    public List<SwitchDevice> findAllSwitchDevices(Pageable pageable) {
        return switchDeviceJpaRepository.findAll(pageable)
                .map(SwitchDeviceEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<SwitchDevice> findSwitchDevicesByOwner(String owner, Pageable pageable) {
        return switchDeviceJpaRepository.findByOwner(owner, pageable)
                .stream()
                .map(SwitchDeviceEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SwitchDevice> findSwitchDeviceById(long switchDeviceId) {
        return switchDeviceJpaRepository.findById(switchDeviceId)
                .map(SwitchDeviceEntityMapper::toDomain);
    }

    @Override
    public void deleteSwitchDevice(SwitchDevice switchDevice) {
        final var switchDeviceToDelete = SwitchDeviceEntityMapper.toEntity(switchDevice);
        switchDeviceJpaRepository.delete(switchDeviceToDelete);
    }

    @Override
    public List<SwitchDevice> findSwitchDevicesByLocationId(long locationId) {
        return switchDeviceJpaRepository.findByLocationSimpleEntity_Id(locationId)
                .stream()
                .map(SwitchDeviceEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SwitchDevice> findSwitchDevicesByLocationIdAndOwner(long locationId, String owner, Pageable pageable) {
        return switchDeviceJpaRepository.findByLocationSimpleEntity_IdAndOwner(locationId, owner, pageable)
                .stream()
                .map(SwitchDeviceEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
