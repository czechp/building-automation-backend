package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountCommandPort;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountNotificationPort;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountAdminActivateUseCase;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountCreateUseCase;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountDeleteByIdUseCase;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountEmailConfirmUseCase;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountCommandDto;
import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiled;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class AccountCommandService implements AccountCreateUseCase,
        AccountDeleteByIdUseCase,
        AccountAdminActivateUseCase,
        AccountEmailConfirmUseCase {
    private final AccountCommandPort accountCommandPort;
    private final AccountNotificationPort accountNotificationPort;

    @Override
    public Account createAccount(AccountCommandDto dto) {
        usernameAndEmailAreUnique(dto);
        final Account account = Account.create(dto);
        Account newAccount = accountCommandPort.saveAccount(account);
        accountNotificationPort.accountCreatedNotification(account.getEmail(), account.getEnableToken());
        return newAccount;
    }

    @Override
    @Transactional
    public void deleteAccountById(long id) {
        accountCommandPort.findAccountById(id)
                .ifPresentOrElse(
                        this::deleteAccount,
                        NotFoundException.getRunnable(notFoundExceptionMsg(id))
                );

    }

    @Override
    @Transactional
    public Account accountAdminActivation(long accountId, boolean activation) {
        final Account account = accountCommandPort.findAccountById(accountId)
                .orElseThrow(() -> new NotFoundException(notFoundExceptionMsg(accountId)));
        Account accountAfterActivation;
        if(activation)
            accountAfterActivation = account.adminActivate();
        else
            accountAfterActivation = account.adminDeactivate();

        accountCommandPort.saveAccount(accountAfterActivation);
        return  accountAfterActivation;
    }

    @Transactional
    @Override
    public Account accountConfirmEmail(String token) {
        Account account = accountCommandPort.findAccountByEnableToken(token)
                .orElseThrow(() -> new NotFoundException("There is no account with such email confirm token: " + token));
        Account accountConfirmed = account.confirmEmail(token);
        accountCommandPort.saveAccount(accountConfirmed);
        return account;
    }

    private String notFoundExceptionMsg(long accountId) {
        return "Account with id: " + accountId + " does not exist";
    }

    private void usernameAndEmailAreUnique(AccountCommandDto dto) {
        accountCommandPort
                .findAccountByUsernameOrEmail(dto.getUsername(), dto.getEmail())
                .ifPresent((r) -> {
                    throw new ConditionsNotFulFiled("Account with such username or email already exists");
                });
    }


    private void deleteAccount(Account account) {
        if (conditionsToDeleteFulfilled(account))
            accountCommandPort.deleteAccount(account);
        else
            throw new ConditionsNotFulFiled("You aren't owner of account");
    }

    private boolean conditionsToDeleteFulfilled(Account account) {
        return userIsAdmin() || userIsAccountOwner(account);
    }

    private boolean userIsAdmin() {
        final Account currentUser = accountCommandPort.findCurrentLoggedUser()
                .orElseThrow(() -> new ConditionsNotFulFiled("There is no current logged user"));
        return currentUser.getAccountRole().equals(AccountRole.ADMIN);
    }

    private boolean userIsAccountOwner(Account account) {
        final Account currentUser = accountCommandPort.findCurrentLoggedUser()
                .orElseThrow(() -> new ConditionsNotFulFiled("There is no current logged user"));
        return account.getUsername().equals(currentUser.getUsername());

    }

}
