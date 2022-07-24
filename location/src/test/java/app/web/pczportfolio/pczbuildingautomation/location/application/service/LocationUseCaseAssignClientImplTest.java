package app.web.pczportfolio.pczbuildingautomation.location.application.service;

import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiledException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotEnoughPrivilegesException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortExistsByClientUUID;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindById;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortSave;
import app.web.pczportfolio.pczbuildingautomation.location.application.useCase.LocationUseCaseAssignClient;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationUseCaseAssignClientImplTest {
    @Mock
    LocationPortFindById locationPortFindById;
    @Mock
    LocationOwnerValidator locationOwnerValidator;
    @Mock
    LocationPortExistsByClientUUID locationPortExistsByClientUUID;
    @Mock
    LocationPortSave locationPortSave;

    LocationUseCaseAssignClient locationUseCaseAssignClient;

    @BeforeEach
    void init() {
        this.locationUseCaseAssignClient = new LocationUseCaseAssignClientImpl(
                locationPortFindById,
                locationOwnerValidator,
                locationPortExistsByClientUUID,
                locationPortSave
        );
    }

    @Test
    void assignClientTest() {
        //given
        final var locationId = 1L;
        final var clientName = "Some new client name";
        final var clientUUID = UUID.randomUUID().toString();
        final var fetchedLocation = Location.builder().build();
        //when
        when(locationPortExistsByClientUUID.locationExistsWithClientUUID(anyString())).thenReturn(false);
        when(locationPortFindById.findLocationById(anyLong())).thenReturn(Optional.of(fetchedLocation));
        when(locationOwnerValidator.currentUserIsOwner(fetchedLocation)).thenReturn(true);
        final var locationWithAssignedClient = locationUseCaseAssignClient.assignClient(
                locationId,
                clientName,
                clientUUID
        );
        //then
        verify(locationPortSave, times(1)).saveLocation(any());
        assertEquals(clientName, locationWithAssignedClient.getClientName());
        assertEquals(clientUUID, locationWithAssignedClient.getClientUUID());
    }

    @Test
    void assignClientLocationWithSuchUUIDAlreadyExistsTest() {
        //given
        final var locationId = 1L;
        final var clientName = "Some new client name";
        final var clientUUID = UUID.randomUUID().toString();
        final var fetchedLocation = Location.builder().build();
        //when
        when(locationPortExistsByClientUUID.locationExistsWithClientUUID(anyString())).thenReturn(true);
        //then
        assertThrows(ConditionsNotFulFiledException.class, () -> locationUseCaseAssignClient.assignClient(
                locationId,
                clientName,
                clientUUID
        ));
    }

    @Test
    void assignClientLocationLocationNotFoundTest() {
        //given
        final var locationId = 1L;
        final var clientName = "Some new client name";
        final var clientUUID = UUID.randomUUID().toString();
        final var fetchedLocation = Location.builder().build();
        //when
        when(locationPortExistsByClientUUID.locationExistsWithClientUUID(anyString())).thenReturn(false);
        when(locationPortFindById.findLocationById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> locationUseCaseAssignClient.assignClient(
                locationId,
                clientName,
                clientUUID
        ));
    }


    @Test
    void assignClientLocationLocationUserIsNotOwnerTest() {
        //given
        final var locationId = 1L;
        final var clientName = "Some new client name";
        final var clientUUID = UUID.randomUUID().toString();
        final var fetchedLocation = Location.builder().build();
        //when
        when(locationPortExistsByClientUUID.locationExistsWithClientUUID(anyString())).thenReturn(false);
        when(locationPortFindById.findLocationById(anyLong())).thenReturn(Optional.of(fetchedLocation));
        when(locationOwnerValidator.currentUserIsOwner(fetchedLocation)).thenReturn(false);
        //then
        assertThrows(NotEnoughPrivilegesException.class, () -> locationUseCaseAssignClient.assignClient(
                locationId,
                clientName,
                clientUUID
        ));
    }

    @Test
    void clearClientTest(){
        //given
        final var locationId = 1L;
        final var fetchedLocation = Location.builder()
                .withClientName("Some name")
                .withClientUUID(UUID.randomUUID().toString())
                .build();
        //when
        when(locationPortFindById.findLocationById(anyLong())).thenReturn(Optional.of(fetchedLocation));
        when(locationOwnerValidator.currentUserIsOwner(fetchedLocation)).thenReturn(true);
        final var locationWithoutClient = locationUseCaseAssignClient.clearClient(locationId);
        //then
        verify(locationPortSave, times(1)).saveLocation(any());
        assertEquals("", locationWithoutClient.getClientName());
        assertEquals("", locationWithoutClient.getClientUUID());
    }

    @Test
    void clearClientNotFoundTest(){
        //given
        final var locationId = 1L;
        final var fetchedLocation = Location.builder()
                .withClientName("Some name")
                .withClientUUID(UUID.randomUUID().toString())
                .build();
        //when
        when(locationPortFindById.findLocationById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, ()-> locationUseCaseAssignClient.clearClient(locationId));
    }


    @Test
    void clearClientUserIsNotOwnerTest(){
        //given
        final var locationId = 1L;
        final var fetchedLocation = Location.builder()
                .withClientName("Some name")
                .withClientUUID(UUID.randomUUID().toString())
                .build();
        //when
        when(locationPortFindById.findLocationById(anyLong())).thenReturn(Optional.of(fetchedLocation));
        when(locationOwnerValidator.currentUserIsOwner(fetchedLocation)).thenReturn(false);
        //then
        assertThrows(NotEnoughPrivilegesException.class, ()-> locationUseCaseAssignClient.clearClient(locationId));
    }
}