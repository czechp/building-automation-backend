package app.web.pczportfolio.pczbuildingautomation.account.adapter.email;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountNotificationPort;
import org.springframework.stereotype.Service;

@Service
class AccountNotifier implements AccountNotificationPort {

    //TODO: Sending email with token to user
    @Override
    public void accountCreatedNotification(String email, String emailConfirmToken) {
        System.out.println("Email: " + email);
        System.out.println("Token: " + emailConfirmToken);
    }
}
