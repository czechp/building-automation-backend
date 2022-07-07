package app.web.pczportfolio.pczbuildingautomation.location.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.exception.NotEnoughPrivilegesException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortDelete;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortEmitEventDelete;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindById;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindCurrentUserAccount;
import app.web.pczportfolio.pczbuildingautomation.location.application.useCase.LocationUseCaseDelete;
import app.web.pczportfolio.pczbuildingautomation.location.domain.AccountParent;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationUseCaseDeleteImplTest {
    @Mock
    LocationPortFindById locationPortFindById;
    @Mock
    LocationPortDelete locationPortDelete;

    @Mock
    LocationPortEmitEventDelete locationPortEmitEventDelete;
    @Mock
    LocationPortFindCurrentUserAccount locationPortFindCurrentUserAccount;


    LocationOwnerValidator locationOwnerValidator;

    LocationUseCaseDelete locationUseCaseDelete;

    @BeforeEach
    void init() {
        this.locationOwnerValidator = new LocationOwnerValidator(locationPortFindCurrentUserAccount);
        this.locationUseCaseDelete = new LocationUseCaseDeleteImpl(locationPortFindById, locationPortDelete, locationPortEmitEventDelete, locationOwnerValidator);
    }

    @Test
    void deleteLocationTest() {
        //given
        final var locationId = 1L;
        final var commonUsername = "Some username";
        final var locationToDelete = Location.builder()
                .withId(locationId)
                .withAccountParent(
                        AccountParent.builder()
                                .withUsername(commonUsername)
                                .build()
                )
                .build();
        final var currentUserAccount = new AccountFacadeDto(1L, commonUsername, "USER");
        //when
        when(locationPortFindById.findLocationById(anyLong())).thenReturn(Optional.of(locationToDelete));
        when(locationPortFindCurrentUserAccount.findAccountOfCurrentUser()).thenReturn(Optional.of(currentUserAccount));
        locationUseCaseDelete.deleteLocationById(locationId);
        //then
        verify(locationPortFindById, times(1)).findLocationById(locationId);
        verify(locationPortDelete, times(1)).deleteLocation(any());
    }

    @Test
    void deleteLocationNotFoundTest() {
        //given
        final var locationId = 1L;

        //when
        when(locationPortFindById.findLocationById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> locationUseCaseDelete.deleteLocationById(locationId));
    }

    @Test
    void deleteLocationIsNotOwnerTest() {
        final var locationId = 1L;
        final var commonUsername = "Some username";
        final var locationToDelete = Location.builder()
                .withId(1L)
                .withAccountParent(
                        AccountParent.builder()
                                .withUsername(commonUsername)
                                .build()
                )
                .build();
        final var currentUserAccount = new AccountFacadeDto(1L, "Different username", "USER");
        //when
        when(locationPortFindById.findLocationById(anyLong())).thenReturn(Optional.of(locationToDelete));
        when(locationPortFindCurrentUserAccount.findAccountOfCurrentUser()).thenReturn(Optional.of(currentUserAccount));
        //then
        assertThrows(NotEnoughPrivilegesException.class, () -> locationUseCaseDelete.deleteLocationById(locationId));
    }

}