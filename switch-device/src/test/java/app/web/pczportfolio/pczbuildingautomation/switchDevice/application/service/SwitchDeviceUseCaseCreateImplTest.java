package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiledException;
import app.web.pczportfolio.pczbuildingautomation.location.dto.LocationFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceCreateDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindLocationByIdAndCurrentUser;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortSave;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SwitchDeviceUseCaseCreateImplTest {
    @Mock
    SwitchDevicePortSave switchDevicePortSave;
    @Mock
    SwitchDevicePortFindLocationByIdAndCurrentUser switchDevicePortFindLocationByIdAndCurrentUser;

    SwitchDeviceUseCaseCreate switchDeviceUseCaseCreate;

    @BeforeEach
    void init() {
        this.switchDeviceUseCaseCreate = new SwitchDeviceUseCaseCreateImpl(
                switchDevicePortSave,
                switchDevicePortFindLocationByIdAndCurrentUser
        );
    }

    @Test
    void createSwitchDeviceTest() {
        //given
        final var locationId = 1L;
        final var switchDeviceToCreate = new SwitchDeviceCreateDto(locationId, "New switch device");
        final var fetchedLocation = new LocationFacadeDto(locationId, "Some location", "Some owner");
        //when
        when(switchDevicePortFindLocationByIdAndCurrentUser.findLocationByIdAndCurrentUser(locationId))
                .thenReturn(Optional.of(fetchedLocation));
        final var switchDeviceCreated = switchDeviceUseCaseCreate.createSwitchDevice(switchDeviceToCreate);
        //then
        verify(switchDevicePortSave, times(1)).saveSwitchDevice(any());
        assertEquals(switchDeviceToCreate.getName(), switchDeviceCreated.getName());
        assertEquals(fetchedLocation.getOwnerUsername(), switchDeviceCreated.getOwner());
        assertFalse(switchDeviceCreated.isExpectedState());
        assertFalse(switchDeviceCreated.isState());
        assertFalse(switchDeviceCreated.isDeviceError());
        assertNotNull(switchDeviceCreated.getLocationParent());
        assertEquals(fetchedLocation.getId(), switchDeviceCreated.getLocationParent().getId());
        assertEquals(fetchedLocation.getName(), switchDeviceCreated.getLocationParent().getName());
    }

    @Test
    void createSwitchDeviceNotFound() {
        //given
        final var locationId = 1L;
        final var switchDeviceToCreate = new SwitchDeviceCreateDto(locationId, "New switch device");
        final var fetchedLocation = new LocationFacadeDto(locationId, "Some location", "Some owner");
        //when
        when(switchDevicePortFindLocationByIdAndCurrentUser.findLocationByIdAndCurrentUser(locationId))
                .thenReturn(Optional.empty());
        //then
        assertThrows(ConditionsNotFulFiledException.class, () -> switchDeviceUseCaseCreate.createSwitchDevice(switchDeviceToCreate));
    }
}