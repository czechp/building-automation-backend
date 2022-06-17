package app.web.pczportfolio.pczbuildingautomation.account.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountCommandDto;

public interface AccountCreateUseCase {
    void createAccount(AccountCommandDto dto);
}
