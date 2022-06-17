package app.web.pczportfolio.pczbuildingautomation.account.adapter.email;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountNotificationPort;
import org.springframework.stereotype.Service;

@Service
class Notifier implements AccountNotificationPort {

    //TODO: create it
    @Override
    public void accountCreatedNotification(String email, String enableToken) {

    }
}
