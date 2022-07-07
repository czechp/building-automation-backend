package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwitchDeviceJpaRepository extends JpaRepository<SwitchDeviceEntity, Long> {
    List<SwitchDeviceEntity> findByOwner(String owner, Pageable pageable);

    List<SwitchDeviceEntity> findByLocationSimpleEntity_Id(long locationId);
}
