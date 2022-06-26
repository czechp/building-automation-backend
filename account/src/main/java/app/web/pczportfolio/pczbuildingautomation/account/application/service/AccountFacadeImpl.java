package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.AccountFacade;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindByUsername;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class AccountFacadeImpl implements AccountFacade {
    private AccountPortFindByUsername accountPortFindByUsername;
    private SecurityCurrentUser securityCurrentUser;

    @Override
    public Optional<AccountFacadeDto> findAccountFacadeDtoByUsername(String username) {
        return accountPortFindByUsername.findAccountByUsername(username)
                .map(this::toFacadeDto);
    }

    @Override
    public Optional<AccountFacadeDto> findCurrentUserAccount() {
        String currentUser = securityCurrentUser.getCurrentUser();
        return accountPortFindByUsername.findAccountByUsername(currentUser)
                .map(this::toDto);
    }

    private AccountFacadeDto toFacadeDto(Account account) {
        return new AccountFacadeDto(account.getId(), account.getUsername(), account.getAccountRole().toString());
    }

    @Override
    public Optional<AccountFacadeDto> findAccountByUsername(String username) {
        return accountPortFindByUsername.findAccountByUsername(username)
                .map(this::toDto);
    }

    private AccountFacadeDto toDto(Account account) {
        return new AccountFacadeDto(
                account.getId(),
                account.getUsername(),
                account.getAccountRole().toString()
        );
    }
}
