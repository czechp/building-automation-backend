package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountCreateUseCase;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountCommandDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin("*")
@AllArgsConstructor
class AccountRestAdapter {
    private final AccountCreateUseCase accountCreateUseCase;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createAccount(@RequestBody @Valid AccountCommandDto accountCommandDto) {
        accountCreateUseCase.createAccount(accountCommandDto);
    }
}
