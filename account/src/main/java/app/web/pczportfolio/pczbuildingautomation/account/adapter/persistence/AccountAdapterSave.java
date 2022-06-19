package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortSave;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AccountAdapterSave implements AccountPortSave {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Account saveAccount(Account account) {
        AccountEntity accountEntity = AccountEntityMapper.toEntity(account);
        return AccountEntityMapper.toDomain(accountJpaRepository.save(accountEntity));
    }
}
