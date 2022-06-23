package app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface LocationJpaRepository extends JpaRepository<LocationEntity, Long> {
}
