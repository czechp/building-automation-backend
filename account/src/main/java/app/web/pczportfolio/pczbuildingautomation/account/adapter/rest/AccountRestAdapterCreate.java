package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountCreateCmdDto;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseCreate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accounts")
@Validated
class AccountRestAdapterCreate {
    private final AccountUseCaseCreate accountUseCaseCreate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createAccount(@RequestBody @Valid AccountCreateCmdDto accountCreateCmdDto) {
        accountUseCaseCreate.createAccount(accountCreateCmdDto);
    }


}
