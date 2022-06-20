package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortDelete;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindById;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindByUsername;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseDelete;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityUtilities;
import app.web.pczportfolio.pczbuildingautomation.exception.NotEnoughPrivilegesException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class AccountUseCaseDeleteImpl implements AccountUseCaseDelete {
    private final AccountPortFindById accountPortFindById;
    private final AccountPortDelete accountPortDelete;
    private final AccountPortFindByUsername accountFindByUsername;
    private final SecurityUtilities securityUtilities;

    @Transactional
    @Override
    public void deleteAccount(long id) {
        accountPortFindById.findAccountById(id)
                .ifPresentOrElse(
                        this::checkOwningAndDelete,
                        NotFoundException.getRunnable("Account with id: " + id + " not exists")
                );
    }

    private void checkOwningAndDelete(Account account) {
        String currentUser = securityUtilities.getCurrentUser();
        boolean userIsAccountOwner = userIsAccountOwner(account, currentUser);
        boolean userIsAdmin = userIsAdmin(currentUser);
        if (userIsAccountOwner || userIsAdmin) {
            accountPortDelete.deleteAccount(account);
        } else
            throw new NotEnoughPrivilegesException("You have no permission to delete this account");
    }

    private boolean userIsAdmin(String currentUser) {
        return accountFindByUsername.findAccountByUsername(currentUser)
                .map(Account::getAccountRole)
                .filter(accountRole -> accountRole.equals(AccountRole.ADMIN))
                .isPresent();
    }

    private boolean userIsAccountOwner(Account account, String currentUser) {
        return account.getUsername().equals(currentUser);
    }
}
