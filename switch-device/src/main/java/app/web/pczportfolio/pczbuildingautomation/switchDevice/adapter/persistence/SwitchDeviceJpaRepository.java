package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwitchDeviceJpaRepository extends JpaRepository<SwitchDeviceEntity, Long> {
    List<SwitchDeviceEntity> findByOwner(String owner, Pageable pageable);
}
