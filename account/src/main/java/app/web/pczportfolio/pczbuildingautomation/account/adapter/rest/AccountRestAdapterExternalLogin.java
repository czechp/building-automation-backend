package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountUserDetailsDto;
import app.web.pczportfolio.pczbuildingautomation.account.application.query.AccountQueryExternalAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/external-login")
@CrossOrigin("*")
@AllArgsConstructor
class AccountRestAdapterExternalLogin {
    private final AccountQueryExternalAuthentication accountQueryExternalAuthentication;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    AccountUserDetailsDto authenticateExternalSystems() {
        return accountQueryExternalAuthentication.authenticateCurrentUser();
    }
}
