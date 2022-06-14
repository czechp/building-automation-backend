package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {
    Optional<Account> findByUsernameOrEmail(String username, String email);
}
