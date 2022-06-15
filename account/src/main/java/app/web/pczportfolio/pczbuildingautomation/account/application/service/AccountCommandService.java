package app.web.pczportfolio.pczbuildingautomation.account.application.service;

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
        Account account = Account.createFromCommandDto(dto);
        accountCommandPort.createAccount(account);
        accountNotificationPort.accountCreatedNotification(account.getEmail(), account.getEnableToken());
    }

    @Override
    public void deleteAccountById(long id) {
        accountCommandPort.findAccountById(id)
                .ifPresentOrElse(
                        accountCommandPort::deleteAccount,
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
}
