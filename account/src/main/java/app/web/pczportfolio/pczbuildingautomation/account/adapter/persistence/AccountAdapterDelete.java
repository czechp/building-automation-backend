package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;


import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortDelete;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AccountAdapterDelete implements AccountPortDelete {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public void deleteAccount(Account account) {
        accountJpaRepository.delete(AccountEntityMapper.toEntity(account));
    }
}
