package app.web.pczportfolio.pczbuildingautomation.location.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityCurrentUser;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationCreateCommandDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindAccountUsername;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortSave;
import app.web.pczportfolio.pczbuildingautomation.location.application.useCase.LocationUseCaseCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationUseCaseCreateImplTest {
    @Mock
    LocationPortSave locationPortSave;
    @Mock
    LocationPortFindAccountUsername locationPortFindAccountUsername;
    @Mock
    SecurityCurrentUser securityCurrentUser;

    LocationUseCaseCreate locationUseCaseCreate;

    @BeforeEach
    void init() {
        this.locationUseCaseCreate = new LocationUseCaseCreateImpl(
                locationPortSave,
                locationPortFindAccountUsername,
                securityCurrentUser
        );
    }

    @Test
    void createLocationTest() {
        //given
        final var accountId = 1L;
        final var locationCommandDto = new LocationCreateCommandDto(
                "Some location name"
        );
        final var fetchedAccountFacadeDto = new AccountFacadeDto(accountId, "Some username");
        final var currentAccountUsername = "Some user";
        //when
        when(locationPortFindAccountUsername.findAccountByUsername(anyString())).thenReturn(Optional.of(fetchedAccountFacadeDto));
        when(securityCurrentUser.getCurrentUser()).thenReturn(currentAccountUsername);
        final var createdLocation = locationUseCaseCreate.createLocation(locationCommandDto);
        //then
        verify(locationPortSave, times(1)).saveLocation(any());
        assertEquals(locationCommandDto.getName(), createdLocation.getName());
        assertEquals(accountId, createdLocation.getAccountParent().getId());
    }

    @Test
    void createLocationTestAccountNotExists() {
        //given
        final var accountId = 1L;
        final var locationCommandDto = new LocationCreateCommandDto(
                "Some location name"
        );
        final var currentAccountUsername = "Some user";
        //when
        when(locationPortFindAccountUsername.findAccountByUsername(anyString())).thenReturn(Optional.empty());
        when(securityCurrentUser.getCurrentUser()).thenReturn(currentAccountUsername);

        //then
        assertThrows(NotFoundException.class, () -> locationUseCaseCreate.createLocation(locationCommandDto));
    }
}