package app.web.pczportfolio.pczbuildingautomation.account.application.port;

public interface AccountPortCreateNotifier {
    void notifyAboutNewAccount(String email, String token);
}
