package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.persistence;


import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public
interface DeviceEventJpaRepository extends JpaRepository<DeviceEventEntity, Long> {
    List<DeviceEventEntity> findByOwner(String owner, Pageable pageable);

    List<DeviceEventEntity> findByOwnerAndDeviceId(String owner, long deviceId);
}
