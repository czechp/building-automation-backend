package app.web.pczportfolio.pczbuildingautomation.account.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;

interface AccountUseCaseRestorePassword {
    Account generateTokenToRestorePassword(long accountId);

    Account activateRestoringNewPassword(String restoreToken);

    Account setNewPassword(long accountId, String newPassword);
}
