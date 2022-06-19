package app.web.pczportfolio.pczbuildingautomation.account.adapter.email;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortCreateNotifier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AccountAdapterCreateNotifier implements AccountPortCreateNotifier {
    //TODO: implement it
    @Override
    public void notifyAboutNewAccount(String email, String token) {
        System.out.println("Email: " + email);
        System.out.println("Token: " + token);
    }
}
