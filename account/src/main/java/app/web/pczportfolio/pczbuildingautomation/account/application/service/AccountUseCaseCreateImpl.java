package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountCreateCmdDto;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortCreateNotifier;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindByUsernameOrEmail;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortSave;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseCreate;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiledException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
class AccountUseCaseCreateImpl implements AccountUseCaseCreate {
    private final PasswordEncoder passwordEncoder;
    private final AccountPortFindByUsernameOrEmail accountPortFindByUsernameOrEmail;
    private final AccountPortCreateNotifier accountPortCreateNotifier;
    private final AccountPortSave accountPortSave;

    @Override
    public Account createAccount(AccountCreateCmdDto accountCreateCmdDto) {
        Function<String, String> hashPasswordFunction = passwordEncoder::encode;
        accountPortFindByUsernameOrEmail.findAccountByUsernameOrEmail(accountCreateCmdDto.getUsername(), accountCreateCmdDto.getPassword())
                .ifPresent((r) -> {
                    throw new ConditionsNotFulFiledException("Account with such username or email already exists");
                });
        Account newAccount = Account.create(accountCreateCmdDto, hashPasswordFunction);
        accountPortSave.saveAccount(newAccount);
        accountPortCreateNotifier.notifyAboutNewAccount(newAccount.getEmail(), newAccount.getEnableToken());
        return newAccount;
    }
}
