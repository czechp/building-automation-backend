package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindByEnableToken;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortSave;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseEmailConfirmation;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.account.domain.AccountConfiguration;
import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiledException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountUseCaseEmailConfirmationImplTest {
    @Mock
    AccountPortSave accountPortSave;
    @Mock
    AccountPortFindByEnableToken accountPortFindByEnableToken;

    AccountUseCaseEmailConfirmation accountUseCaseEmailConfirmation;

    @BeforeEach
    void init() {
        this.accountUseCaseEmailConfirmation = new AccountUseCaseEmailConfirmationImpl(accountPortFindByEnableToken, accountPortSave);
    }

    @Test
    void accountEmailConfirmationTest() {
        //given
        final String token = "123321";
        final Account fetchedAccount = Account.builder()
                .withAccountConfiguration(
                        AccountConfiguration.builder()
                                .withEmailConfirmed(false)
                                .withEnableToken(token)
                                .build()
                )
                .build();
        //when
        when(accountPortFindByEnableToken.findAccountByEnableToken(anyString())).thenReturn(Optional.of(fetchedAccount));
        final Account accountEmailConfirmed = accountUseCaseEmailConfirmation.accountEmailConfirmation(token);
        //then
        assertTrue(accountEmailConfirmed.getAccountConfiguration().isEmailConfirmed());
        verify(accountPortSave, times(1)).saveAccount(fetchedAccount);
    }

    @Test
    void accountEmailConfirmationAccountNotExistsTest() {
        //given
        final String token = "123321";
        //when
        when(accountPortFindByEnableToken.findAccountByEnableToken(anyString())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> accountUseCaseEmailConfirmation.accountEmailConfirmation(token));
        verify(accountPortSave, times(0)).saveAccount(any());
    }

    @Test
    void accountEmailConfirmationTestTokenNotEquals() {
        //given
        final String token = "123321";
        final Account fetchedAccount = Account.builder()
                .withAccountConfiguration(
                        AccountConfiguration.builder()
                                .withEmailConfirmed(false)
                                .withEnableToken("differentToken")
                                .build()
                )
                .build();
        //when
        when(accountPortFindByEnableToken.findAccountByEnableToken(anyString())).thenReturn(Optional.of(fetchedAccount));
        //then
        assertThrows(ConditionsNotFulFiledException.class, () -> accountUseCaseEmailConfirmation.accountEmailConfirmation(token));

    }

}