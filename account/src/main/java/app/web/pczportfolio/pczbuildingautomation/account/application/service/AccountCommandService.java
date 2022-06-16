package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountCommandPort;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountNotificationPort;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountCreateUseCase;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountDeleteByIdUseCase;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountCommandDto;
import app.web.pczportfolio.pczbuildingautomation.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AccountCommandService implements AccountCreateUseCase, AccountDeleteByIdUseCase {
    private final AccountCommandPort accountCommandPort;
    private final AccountNotificationPort accountNotificationPort;

    @Override
    public void createAccount(AccountCommandDto dto) {
        usernameAndEmailAreUnique(dto);
        Account account = Account.create(dto);
        accountCommandPort.createAccount(account);
        accountNotificationPort.accountCreatedNotification(account.getEmail(), account.getEnableToken());
    }

    @Override
    public void deleteAccountById(long id) {
        accountCommandPort.findAccountById(id)
                .ifPresentOrElse(
                        this::deleteAccount,
                        throwBadRequestException("Account with id: " + id + " not exists")
                );

    }


    private void usernameAndEmailAreUnique(AccountCommandDto dto) {
        accountCommandPort
                .findAccountByUsernameOrEmail(dto.getUsername(), dto.getEmail())
                .ifPresent((r) -> {
                    throwBadRequestException("Account with such username or email already exists").run();
                });
    }

    private Runnable throwBadRequestException(String message) {
        return () -> {
            throw new BadRequestException(message);
        };
    }


    private void deleteAccount(Account account) {
        if(conditionsToDeleteFulfilled(account))
            accountCommandPort.deleteAccount(account);
        else
            throw new BadRequestException("You aren't owner of account");
    }

    private boolean conditionsToDeleteFulfilled(Account account) {
        return userIsAdmin() || userIsAccountOwner(account);
    }

    private boolean userIsAdmin() {
        Account currentUser = accountCommandPort.findCurrentLoggedUser()
                .orElseThrow(() -> new BadRequestException("There is no current logged user"));
        return currentUser.getAccountRole().equals(AccountRole.ADMIN);
    }

    private boolean userIsAccountOwner(Account account){
        Account currentUser = accountCommandPort.findCurrentLoggedUser()
                .orElseThrow(() -> new BadRequestException("There is no current logged user"));
        return account.getUsername().equals(currentUser.getUsername());

    }

}
