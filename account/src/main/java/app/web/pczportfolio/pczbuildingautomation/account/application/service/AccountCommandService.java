package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.*;
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
    private final AccountCreatePort accountCreatePort;
    private final AccountFindByUsernameOrEmailPort accountFindByUsernameOrEmailPort;
    private final AccountCreateEmailNotificationPort accountCreateEmailNotificationPort;

    private final AccountFindByIdPort accountFindByIdPort;
    private final AccountDeletePort accountDeletePort;

    @Override
    public void createAccount(AccountCommandDto dto) {
        usernameAndEmailAreUnique(dto);
        Account account = Account.createFromCommandDto(dto);
        accountCreatePort.createAccount(account);
        accountCreateEmailNotificationPort.accountCreatedNotification(account.getEmail(), account.getEnableToken());
    }

    @Override
    public void deleteAccountById(long id) {
        accountFindByIdPort.findAccountById(id)
                .ifPresentOrElse(
                        accountDeletePort::deleteAccount,
                        throwBadRequestException("Account with id: " + id + " not exists")
                );

    }


    private void usernameAndEmailAreUnique(AccountCommandDto dto) {
        accountFindByUsernameOrEmailPort
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
}
