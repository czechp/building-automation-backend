package app.web.pczportfolio.pczbuildingautomation.configuration.security;

public interface SecurityAuthenticator {
    void authenticateAccount(String username, String password);

}
