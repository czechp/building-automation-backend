package app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortSave;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class LocationPersistenceAdapter implements LocationPortSave {
    private final LocationJpaRepository locationJpaRepository;

    @Override
    public Location saveLocation(Location location) {
        LocationEntity locationEntity = LocationEntityMapper.mapToEntity(location);
        return LocationEntityMapper.mapToDomain(locationJpaRepository.save(locationEntity));
    }
}
