package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public
interface DeviceEventJpaRepository extends JpaRepository<DeviceEventEntity, Long> {
}
