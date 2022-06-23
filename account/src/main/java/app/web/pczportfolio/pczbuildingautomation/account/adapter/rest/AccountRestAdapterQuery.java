package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountQueryDto;
import app.web.pczportfolio.pczbuildingautomation.account.application.query.AccountQuery;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accounts")
class AccountRestAdapterQuery {
    private final AccountQuery accountQuery;

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<AccountQueryDto> findAccountsAll() {
        return accountQuery.findAccountsAll();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    AccountQueryDto findAccountById(@PathVariable(name = "id") long accountId) {
        return accountQuery.findAccountById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with such id: " + accountId + " does not exist"));
    }
}
