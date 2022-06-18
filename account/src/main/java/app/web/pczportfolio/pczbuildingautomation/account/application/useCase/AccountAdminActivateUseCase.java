package app.web.pczportfolio.pczbuildingautomation.account.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;

public interface AccountAdminActivateUseCase {
    Account accountAdminActivation(long accountId, boolean activation);
}
