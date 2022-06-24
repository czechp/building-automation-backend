package app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindAll;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindByAccountId;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindById;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortSave;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class LocationPersistenceAdapter implements
        LocationPortSave,
        LocationPortFindAll,
        LocationPortFindByAccountId,
        LocationPortFindById {
    private final LocationJpaRepository locationJpaRepository;

    @Override
    public Location saveLocation(Location location) {
        LocationEntity locationEntity = LocationEntityMapper.mapToEntity(location);
        return LocationEntityMapper.mapToDomain(locationJpaRepository.save(locationEntity));
    }

    @Override
    public List<Location> findLocationsAll() {
        return locationJpaRepository.findAll()
                .stream()
                .map(LocationEntityMapper::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Location> findLocationsByAccountId(long accountId) {
        return locationJpaRepository.findByAccountSimpleEntity_Id(accountId)
                .stream()
                .map(LocationEntityMapper::mapToDomain)
                .collect(Collectors.toList());

    }

    @Override
    public Optional<Location> findLocationById(long locationId) {
        return locationJpaRepository.findById(locationId)
                .map(LocationEntityMapper::mapToDomain);
    }
}
