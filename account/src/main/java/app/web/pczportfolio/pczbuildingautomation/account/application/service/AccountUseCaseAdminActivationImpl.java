package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindById;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortSave;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseAdminActivation;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class AccountUseCaseAdminActivationImpl implements AccountUseCaseAdminActivation {
    private final AccountPortFindById accountPortFindById;
    private final AccountPortSave accountPortSave;

    @Override
    @Transactional
    public Account accountAdminActivation(long id, boolean activation) {
        Account account = accountPortFindById.findAccountById(id)
                .orElseThrow(() -> new NotFoundException("Account with id: " + id + " not exists"));
        if (activation)
            account.adminActivate();
        else
            account.adminDeactivate();

        accountPortSave.saveAccount(account);
        return account;
    }
}
