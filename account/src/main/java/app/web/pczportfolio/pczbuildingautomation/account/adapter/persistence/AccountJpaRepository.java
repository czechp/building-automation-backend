package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {
}
