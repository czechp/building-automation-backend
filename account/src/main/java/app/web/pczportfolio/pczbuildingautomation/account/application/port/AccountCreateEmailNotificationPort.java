package app.web.pczportfolio.pczbuildingautomation.account.application.port;

public interface AccountCreateEmailNotificationPort {
    void accountCreatedNotification(String email, String enableToken);
}
