package app.web.pczportfolio.pczbuildingautomation.account.application.dto;

import app.web.pczportfolio.pczbuildingautomation.utilities.validator.NoWhiteSpacesValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountCreateCmdDto {
    @NotBlank(message = "Username cannot be blank")
    @Length(min = 3, message = "Username has to have minimum 3 characters")
    @NoWhiteSpacesValidator(message = "Username cannot include white spaces")
    private String username;

    @Email(message = "It's incorrect email format")
    private String email;

    @Length(min = 3, message = "Username has to have minimum 3 characters")
    @NoWhiteSpacesValidator(message = "Password cannot include white spaces")
    private String password;

    @Length(min = 3, message = "Username has to have minimum 3 characters")
    @NoWhiteSpacesValidator(message = "Password cannot include white spaces")
    private String passwordConfirm;
}
