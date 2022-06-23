package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;


import app.web.pczportfolio.pczbuildingautomation.account.adapter.rest.dto.LoginDto;
import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityAuthenticator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accounts/login")
class AccountRestAdapterLogin {
    private final SecurityAuthenticator securityAuthenticator;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<HashMap<String, String>> login(@RequestBody @Valid LoginDto loginDto) {
        try {
            securityAuthenticator.authenticateAccount(loginDto.getUsername(), loginDto.getPassword());
        } catch (DisabledException disabledException) {
            return buildResponse("Account is currently disabled", HttpStatus.UNAUTHORIZED);
        } catch (LockedException lockedException) {
            return buildResponse("Account is currently locked", HttpStatus.UNAUTHORIZED);
        } catch (BadCredentialsException badCredentialsException) {
            return buildResponse("There is no account with such credentials", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    private ResponseEntity<HashMap<String, String>> buildResponse(String message, HttpStatus httpStatus) {
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("timestamp", LocalDateTime.now().toString());
        responseBody.put("message", message);
        return new ResponseEntity<>(responseBody, httpStatus);
    }


}
