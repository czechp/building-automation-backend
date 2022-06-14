package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountCreateEmailNotificationPort;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountCreatePort;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountFindByUsernameOrEmailPort;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountCreateUseCase;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountCommandDto;
import app.web.pczportfolio.pczbuildingautomation.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AccountCommandService implements AccountCreateUseCase {
    private final AccountCreatePort accountCreatePort;
    private final AccountFindByUsernameOrEmailPort findByUsernameOrEmailPort;
    private final AccountCreateEmailNotificationPort emailNotificationPort;

    @Override
    public void createAccount(AccountCommandDto dto) {
        usernameAndEmailAreUnique(dto);
        Account account = Account.create(dto);
        accountCreatePort.createAccount(account);
        emailNotificationPort.accountCreatedNotification(account.getEmail(), account.getEnableToken());

    }

    private void usernameAndEmailAreUnique(AccountCommandDto dto) {
        findByUsernameOrEmailPort
                .findByUsernameOrEmail(dto.getUsername(), dto.getEmail())
                .ifPresent((r) -> {
                    throw new BadRequestException("Account with username or email already exists");
                });
    }

}
