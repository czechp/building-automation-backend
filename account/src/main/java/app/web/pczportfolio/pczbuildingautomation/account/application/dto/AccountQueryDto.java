package app.web.pczportfolio.pczbuildingautomation.account.application.dto;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountQueryDto {
    private long id;
    private String username;
    private String email;
    private String role;

    public static AccountQueryDto toAccountQueryDto(Account account){
        return new AccountQueryDto(
                account.getId(),
                account.getUsername(),
                account.getEmail(),
                account.getAccountRole().toString()
        );
    }
}
