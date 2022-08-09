package app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationQueryDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class LocationPersistenceAdapterQuery implements LocationPortQuery {
    private final LocationJpaRepository locationJpaRepository;

    @Override
    public List<LocationQueryDto> findAllLocations(Pageable pageable) {
        return locationJpaRepository.findAllQuery(pageable);
    }

    @Override
    public List<LocationQueryDto> findLocationsByAccountUsername(String username, Pageable pageable) {
        return locationJpaRepository.findByAccountUsernameQuery(username, pageable);
    }

    @Override
    public Optional<LocationQueryDto> findLocationById(long locationId) {
        return locationJpaRepository.findByIdQuery(locationId);
    }
}
