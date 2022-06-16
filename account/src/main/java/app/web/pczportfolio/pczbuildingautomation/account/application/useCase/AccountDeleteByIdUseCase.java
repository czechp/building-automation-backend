package app.web.pczportfolio.pczbuildingautomation.account.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;

import java.util.Optional;

public abstract class AccountDeleteByIdUseCase {
    public abstract void deleteAccountById(long id);
}
