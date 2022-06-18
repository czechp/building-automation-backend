package app.web.pczportfolio.pczbuildingautomation.account.domain;

import java.util.HashMap;
import java.util.Map;

//Only used for tests
public class AccountTestUseCases {
    public static Map<String, Account> cases = Map.of(
            "accountToEmailConfirmed", Account.builder().enableToken("12345").emailConfirmed(false).build()
    );
}
