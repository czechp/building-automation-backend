package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;


import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseDelete;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accounts")
class AccountRestAdapterDelete {
    private final AccountUseCaseDelete accountUseCaseDelete;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAccount(@PathVariable(name = "id") long id) {
        accountUseCaseDelete.deleteAccountById(id);
    }
}
