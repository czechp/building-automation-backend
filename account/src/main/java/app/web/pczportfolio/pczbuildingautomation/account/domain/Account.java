package app.web.pczportfolio.pczbuildingautomation.account.domain;

import lombok.*;

@Getter
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private long id;
    private String username;
    private String password;

    private String email;

    Account(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
