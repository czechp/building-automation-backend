package app.web.pczportfolio.pczbuildingautomation.account.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;

public interface AccountUseCaseAssignRole {
    Account accountAssignRole(long accountId, AccountRole newRole);
}
