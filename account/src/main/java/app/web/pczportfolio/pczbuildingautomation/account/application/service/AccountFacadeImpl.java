package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.AccountFacade;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindById;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindByUsername;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class AccountFacadeImpl implements AccountFacade {
    private AccountPortFindByUsername accountPortFindByUsername;
    @Override
    public Optional<AccountFacadeDto> findAccountFacadeDtoByUsername(String username) {
        return accountPortFindByUsername.findAccountByUsername(username)
                .map(this::toFacadeDto);
    }

    private AccountFacadeDto toFacadeDto(Account account) {
        return new AccountFacadeDto(account.getId(), account.getUsername());
    }
}
