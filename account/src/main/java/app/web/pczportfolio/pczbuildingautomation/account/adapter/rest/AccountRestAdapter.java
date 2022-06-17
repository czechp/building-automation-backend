package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountCreateUseCase;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountDeleteByIdUseCase;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountCommandDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin("*")
@AllArgsConstructor
class AccountRestAdapter {
    private final AccountCreateUseCase accountCreateUseCase;
    private final AccountDeleteByIdUseCase accountDeleteByIdUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createAccount(@RequestBody @Valid AccountCommandDto accountCommandDto) {
        accountCreateUseCase.createAccount(accountCommandDto);
    }

    @Secured("ADMIN")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAccount(@PathVariable(name = "id") long id) {
        accountDeleteByIdUseCase.deleteAccountById(id);
    }
}
