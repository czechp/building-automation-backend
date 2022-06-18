package app.web.pczportfolio.pczbuildingautomation.account.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;

public interface AccountEmailConfirmUseCase {
    Account accountConfirmEmail(String token);
}
