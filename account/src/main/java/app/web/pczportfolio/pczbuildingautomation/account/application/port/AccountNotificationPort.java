package app.web.pczportfolio.pczbuildingautomation.account.application.port;

public interface AccountNotificationPort {
    void accountCreatedNotification(String email, String emailConfirmToken);
}
