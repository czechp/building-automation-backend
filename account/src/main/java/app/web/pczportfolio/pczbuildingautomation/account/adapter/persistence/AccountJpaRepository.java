package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByUsernameOrEmail(String username, String email);

    Optional<AccountEntity> findByUsername(String username);
}
