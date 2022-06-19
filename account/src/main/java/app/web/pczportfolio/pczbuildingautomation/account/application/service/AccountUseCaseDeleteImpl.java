package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortDelete;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindById;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseDelete;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AccountUseCaseDeleteImpl implements AccountUseCaseDelete {
    private final AccountPortFindById accountPortFindById;
    private final AccountPortDelete accountPortDelete;

    @Override
    public void deleteAccount(long id) {
        accountPortFindById.findAccountById(id)
                .ifPresentOrElse(
                        accountPortDelete::deleteAccount,
                        NotFoundException.getRunnable("Account with id: " + id + " not exists")
                );
    }
}
