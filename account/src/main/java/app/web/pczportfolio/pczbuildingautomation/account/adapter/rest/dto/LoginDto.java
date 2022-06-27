package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class LoginDto {
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Password cannot be null")
    private String password;

    public LoginDto() {
    }
}
