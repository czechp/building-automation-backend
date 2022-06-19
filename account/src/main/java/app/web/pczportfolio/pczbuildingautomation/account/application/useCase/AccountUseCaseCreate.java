package app.web.pczportfolio.pczbuildingautomation.account.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountCreateCmdDto;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;

public interface AccountUseCaseCreate {
    Account createAccount(AccountCreateCmdDto accountCreateCmdDto);
}
