package app.web.pczportfolio.pczbuildingautomation.account.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;

public interface AccountUseCaseRestorePassword {
    Account generateTokenToRestorePassword(String email);

    Account setNewPassword(String token, String newPassword);
}
