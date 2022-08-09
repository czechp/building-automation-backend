package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseAdminActivation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accounts/admin-activation")
@CrossOrigin("*")
class AccountRestAdapterAdminActivation {
    private final AccountUseCaseAdminActivation accountUseCaseAdminActivation;

    @Secured({"ROLE_ADMIN"})
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void accountAdminActivation(
            @PathVariable(name = "id") final long id,
            @RequestParam(name = "activation") final boolean activation
    ) {
        accountUseCaseAdminActivation.accountAdminActivation(id, activation);
    }
}
