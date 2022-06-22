package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindById;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortSave;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseAssignRole;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AccountUseCaseAssignRoleImpl implements AccountUseCaseAssignRole {
    private final AccountPortFindById accountPortFindById;
    private final AccountPortSave accountPortSave;

    @Override
    public Account accountAssignRole(long accountId, AccountRole newRole) {
        Account account = accountPortFindById.findAccountById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with id: " + accountId + " not exists"));
        account.assignRole(newRole);
        accountPortSave.saveAccount(account);
        return account;
    }
}
