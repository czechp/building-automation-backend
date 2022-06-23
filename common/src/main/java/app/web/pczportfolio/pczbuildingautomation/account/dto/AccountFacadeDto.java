package app.web.pczportfolio.pczbuildingautomation.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AccountFacadeDto {
    private long id;
    private String username;
}
