package app.web.pczportfolio.pczbuildingautomation.location.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindCurrentUserAccount;
import app.web.pczportfolio.pczbuildingautomation.location.domain.AccountParent;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationOwnerValidatorTest {
    @Mock
    LocationPortFindCurrentUserAccount locationPortFindCurrentUserAccount;

    LocationOwnerValidator locationOwnerValidator;

    @BeforeEach
    void init() {
        this.locationOwnerValidator = new LocationOwnerValidator(locationPortFindCurrentUserAccount);
    }

    @Test
    void currentUserIsOwnerTest() {
        //given
        final var commonUsername = "SomeUserName";
        final var locationToValidate = Location.builder()
                .withAccountParent(
                        AccountParent.builder()
                                .withUsername(commonUsername)
                                .build()
                )
                .build();
        final var accountFromFacade = new AccountFacadeDto(10L, commonUsername, "USER");
        //when
        when(locationPortFindCurrentUserAccount.findAccountOfCurrentUser()).thenReturn(Optional.of(accountFromFacade));
        final var isOwner =locationOwnerValidator.currentUserIsOwner(locationToValidate);
        //then
        assertTrue(isOwner);
    }

    @Test
    void currentUserIsAdminTest() {
        //given
        final var commonUsername = "SomeUserName";
        final var locationToValidate = Location.builder()
                .withAccountParent(
                        AccountParent.builder()
                                .withUsername("Different name")
                                .build()
                )
                .build();
        final var accountFromFacade = new AccountFacadeDto(10L, commonUsername, "ADMIN");
        //when
        when(locationPortFindCurrentUserAccount.findAccountOfCurrentUser()).thenReturn(Optional.of(accountFromFacade));
        final var isOwner =locationOwnerValidator.currentUserIsOwner(locationToValidate);
        //then
        assertTrue(isOwner);
    }

    @Test
    void currentUserIsNotOwnerTest() {
        //given
        final var commonUsername = "SomeUserName";
        final var locationToValidate = Location.builder()
                .withAccountParent(
                        AccountParent.builder()
                                .withUsername("DifferentName")
                                .build()
                )
                .build();
        final var accountFromFacade = new AccountFacadeDto(10L, commonUsername, "USER");
        //when
        when(locationPortFindCurrentUserAccount.findAccountOfCurrentUser()).thenReturn(Optional.of(accountFromFacade));
        final var isOwner =locationOwnerValidator.currentUserIsOwner(locationToValidate);
        //then
        assertFalse(isOwner);
    }
}