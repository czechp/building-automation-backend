package app.web.pczportfolio.pczbuildingautomation.account.application.port;

public interface AccountEmailNotificationPort {
    void accountCreatedNotification(String email, String enableToken);
}
