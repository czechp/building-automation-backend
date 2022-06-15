package app.web.pczportfolio.pczbuildingautomation.account.adapter.email;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountEmailNotificationPort;
import org.springframework.stereotype.Service;

@Service
class EmailNotifier implements AccountEmailNotificationPort {

    //TODO: create it
    @Override
    public void accountCreatedNotification(String email, String enableToken) {

    }
}
