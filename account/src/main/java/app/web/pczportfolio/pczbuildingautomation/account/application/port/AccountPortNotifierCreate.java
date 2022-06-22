package app.web.pczportfolio.pczbuildingautomation.account.application.port;

public interface AccountPortNotifierCreate {
    void notifyAboutNewAccount(String email, String token);
}
