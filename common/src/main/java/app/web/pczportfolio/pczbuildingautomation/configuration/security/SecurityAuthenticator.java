package app.web.pczportfolio.pczbuildingautomation.configuration.security;

import app.web.pczportfolio.pczbuildingautomation.configuration.security.dto.SecurityLoginQueryDto;

public interface SecurityAuthenticator {
    SecurityLoginQueryDto authenticateAccount(String username, String password);

}
