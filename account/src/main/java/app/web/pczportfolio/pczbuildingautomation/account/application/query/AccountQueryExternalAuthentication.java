package app.web.pczportfolio.pczbuildingautomation.account.application.query;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountUserDetailsDto;

public interface AccountQueryExternalAuthentication {
    AccountUserDetailsDto authenticateCurrentUser();
}
