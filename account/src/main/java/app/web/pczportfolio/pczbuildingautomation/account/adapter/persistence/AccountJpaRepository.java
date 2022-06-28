package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountQueryDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByUsernameOrEmail(String username, String email);

    Optional<AccountEntity> findByUsername(String username);

    Optional<AccountEntity> findByAccountConfigurationEmbEnableToken(String token);

    Optional<AccountEntity> findByEmail(String email);

    Optional<AccountEntity> findByAccountConfigurationEmbNewPasswordToken(String newPasswordToken);

    @Query("SELECT a FROM AccountEntity a WHERE a.id=:accountId")
    Optional<AccountQueryDto> findByIdQuery(@Param("accountId") long accountId);

    @Query("SELECT a FROM AccountEntity a")
    List<AccountQueryDto> findAllQuery(Pageable pageable);

}
