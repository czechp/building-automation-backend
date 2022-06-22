package app.web.pczportfolio.pczbuildingautomation.account.application.port;

public interface AccountPortNotifierRestorePasswordToken {
    void sendRestorePasswordToken(String email, String token);
}
