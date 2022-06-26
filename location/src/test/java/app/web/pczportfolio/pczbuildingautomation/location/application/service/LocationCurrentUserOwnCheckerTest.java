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
class LocationCurrentUserOwnCheckerTest {
    @Mock
    LocationPortFindCurrentUserAccount locationPortFindCurrentUserAccount;

    LocationCurrentUserOwnChecker locationCurrentUserOwnChecker;

    @BeforeEach
    void init() {
        this.locationCurrentUserOwnChecker = new LocationCurrentUserOwnChecker(locationPortFindCurrentUserAccount);
    }

    @Test
    void checkCurrentUserOwningTest() {
        //given
        final var commonUsername = "Some user";
        final var currentUserAccount = new AccountFacadeDto(1L, commonUsername, "USER");
        final var location = Location.builder()
                .withAccountParent(
                        AccountParent.builder()
                                .withUsername(commonUsername)
                                .build()
                )
                .build();
        //when
        when(locationPortFindCurrentUserAccount.findAccountOfCurrentUser()).thenReturn(Optional.of(currentUserAccount));
        final var isOwner = locationCurrentUserOwnChecker.checkCurrentUserOwning(location);
        //then
        assertTrue(isOwner);
    }


    @Test
    void checkCurrentUserOwningIsNotOwnerTest() {
        //given
        final var commonUsername = "Some user";
        final var currentUserAccount = new AccountFacadeDto(1L, commonUsername, "USER");
        final var location = Location.builder()
                .withAccountParent(
                        AccountParent.builder()
                                .withUsername("Different username")
                                .build()
                )
                .build();
        //when
        when(locationPortFindCurrentUserAccount.findAccountOfCurrentUser()).thenReturn(Optional.of(currentUserAccount));
        final var isOwner = locationCurrentUserOwnChecker.checkCurrentUserOwning(location);
        //then
        assertFalse(isOwner);
    }


    @Test
    void checkCurrentUserOwningIsNotOwnerButAdminTest() {
        //given
        final var commonUsername = "Some user";
        final var currentUserAccount = new AccountFacadeDto(1L, commonUsername, "ADMIN");
        final var location = Location.builder()
                .withAccountParent(
                        AccountParent.builder()
                                .withUsername("Different username")
                                .build()
                )
                .build();
        //when
        when(locationPortFindCurrentUserAccount.findAccountOfCurrentUser()).thenReturn(Optional.of(currentUserAccount));
        final var isAdmin = locationCurrentUserOwnChecker.checkCurrentUserOwning(location);
        //then
        assertTrue(isAdmin);
    }
}