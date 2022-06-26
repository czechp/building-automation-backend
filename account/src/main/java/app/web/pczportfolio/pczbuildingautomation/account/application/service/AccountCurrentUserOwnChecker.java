package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountQueryDto;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindByUsername;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityCurrentUser;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountCurrentUserOwnChecker {
    private final SecurityCurrentUser securityCurrentUser;
    private final AccountPortFindByUsername accountPortFindByUsername;

    public boolean isAccountOwner(Account account) {
        return validateOwning(account.getUsername());
    }

    public boolean isAccountOwner(AccountQueryDto accountQueryDto) {
        return validateOwning(accountQueryDto.getUsername());
    }

    private boolean validateOwning(String username) {
        final var currentUserAccount = getCurrentUserAccount();
        return currentUserAccount.getUsername().equals(username) || currentUserAccount.getAccountRole().equals(AccountRole.ADMIN);
    }

    private Account getCurrentUserAccount() {
        final var currentUsername = securityCurrentUser.getCurrentUser();
        return accountPortFindByUsername.findAccountByUsername(currentUsername).orElseThrow(() -> new NotFoundException("There is no logged user"));
    }


}
