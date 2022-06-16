package app.web.pczportfolio.pczbuildingautomation.account.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;

import java.util.Optional;

public interface  AccountDeleteByIdUseCase {
    public  void deleteAccountById(long id);
}
