package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindByUsername;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityCurrentUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountOwnerValidatorTest {
    @Mock
    SecurityCurrentUser securityCurrentUser;
    @Mock
    AccountPortFindByUsername accountPortFindByUsername;

    AccountOwnerValidator accountOwnerValidator;

    @BeforeEach
    void init() {
        this.accountOwnerValidator = new AccountOwnerValidator(securityCurrentUser, accountPortFindByUsername);
    }

    @Test
    void isAccountOwnerTest() {
        //given
        final var commonUsername = "Some common username";
        final var currentUserAccount = Account.builder()
                .withUsername(commonUsername)
                .withAccountRole(AccountRole.ADMIN)
                .build();
        final var fetchedAccount = Account.builder()
                .withUsername(commonUsername)
                .build();
        //when
        when(securityCurrentUser.getCurrentUser()).thenReturn(commonUsername);
        when(accountPortFindByUsername.findAccountByUsername(any())).thenReturn(Optional.of(fetchedAccount));
        boolean isOwner = accountOwnerValidator.isAccountOwner(currentUserAccount);
        //then
        assertTrue(isOwner);
    }


    @Test
    void isAccountOwnerIsNotOwnerTest() {
        //given
        final var commonUsername = "Some common username";
        final var account = Account.builder()
                .withUsername(commonUsername)
                .build();
        final var currentUserAccount = Account.builder()
                .withUsername("Different username")
                .withAccountRole(AccountRole.USER)
                .build();
        //when
        when(securityCurrentUser.getCurrentUser()).thenReturn(commonUsername);
        when(accountPortFindByUsername.findAccountByUsername(any())).thenReturn(Optional.of(currentUserAccount));
        boolean isOwner = accountOwnerValidator.isAccountOwner(account);
        //then
        assertFalse(isOwner);
    }


    @Test
    void isAccountOwnerIsAdminTest() {
        //given
        final var commonUsername = "Some common username";
        final var account = Account.builder()
                .withUsername(commonUsername)
                .build();
        final var currentUserAccount = Account.builder()
                .withUsername("Different username")
                .withAccountRole(AccountRole.ADMIN)
                .build();
        //when
        when(securityCurrentUser.getCurrentUser()).thenReturn(commonUsername);
        when(accountPortFindByUsername.findAccountByUsername(any())).thenReturn(Optional.of(currentUserAccount));
        boolean isAdmin = accountOwnerValidator.isAccountOwner(account);
        //then
        assertTrue(isAdmin);
    }
}