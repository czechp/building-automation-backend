package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwitchDeviceJpaRepository extends JpaRepository<SwitchDeviceEntity, Long> {
}
