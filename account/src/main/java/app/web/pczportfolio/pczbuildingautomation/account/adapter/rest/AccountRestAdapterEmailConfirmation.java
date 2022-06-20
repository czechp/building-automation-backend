package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;


import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseEmailConfirmation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accounts")
class AccountRestAdapterEmailConfirmation {
    private final AccountUseCaseEmailConfirmation accountUseCaseEmailConfirmation;

    @PatchMapping("/email-confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void accountEmailConfirmation(
            @RequestParam(name = "token") String token
    ) {
        accountUseCaseEmailConfirmation.accountEmailConfirmation(token);
    }
}
