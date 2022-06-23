package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDto {
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Password cannot be null")
    private String password;

    LoginDto() {
    }
}
