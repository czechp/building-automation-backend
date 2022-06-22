package app.web.pczportfolio.pczbuildingautomation.account.adapter.email;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortNotifierCreate;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortNotifierRestorePasswordToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AccountAdapterNotifier implements AccountPortNotifierCreate, AccountPortNotifierRestorePasswordToken {
    //TODO: implement it
    @Override
    public void notifyAboutNewAccount(String email, String token) {
        System.out.println("New account created:");
        System.out.println("Email: " + email);
        System.out.println("Token: " + token);
    }

    @Override
    public void sendRestorePasswordToken(String email, String token) {
        System.out.println("New token to restore password:");
        System.out.println("Email: " + email);
        System.out.println("Token: " + token);
    }
}
