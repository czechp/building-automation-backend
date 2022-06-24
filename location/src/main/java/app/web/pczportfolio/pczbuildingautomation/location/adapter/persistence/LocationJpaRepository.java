package app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface LocationJpaRepository extends JpaRepository<LocationEntity, Long> {
    List<Location> findByAccountSimpleEntity_Id(long accountId);
}
