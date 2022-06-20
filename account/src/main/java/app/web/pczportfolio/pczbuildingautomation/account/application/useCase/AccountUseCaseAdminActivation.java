package app.web.pczportfolio.pczbuildingautomation.account.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;

public interface AccountUseCaseAdminActivation {
    Account accountAdminActivation(long id, boolean activation);
}
