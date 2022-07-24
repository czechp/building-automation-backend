package app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationQueryDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationJpaRepository extends JpaRepository<LocationEntity, Long> {
    List<LocationEntity> findByAccountSimpleEntity_Id(long accountId);

    @Query("SELECT l from LocationEntity l")
    List<LocationQueryDto> findAllQuery(Pageable pageable);

    @Query("SELECT l from LocationEntity l WHERE l.accountSimpleEntity.username=:username")
    List<LocationQueryDto> findByAccountUsernameQuery(@Param("username") String username);

    @Query("SELECT l FROM LocationEntity l WHERE l.id=:id")
    Optional<LocationQueryDto> findByIdQuery(@Param("id") long locationId);

    Optional<LocationEntity> findByIdAndAccountSimpleEntity_Username(long locationId, String username);

    boolean existsByClientUUID(String clientUUID);
}
