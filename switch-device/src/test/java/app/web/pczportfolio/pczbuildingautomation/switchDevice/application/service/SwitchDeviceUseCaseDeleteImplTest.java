package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.exception.NotEnoughPrivilegesException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortDelete;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindById;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseDelete;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.LocationParent;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SwitchDeviceUseCaseDeleteImplTest {
    @Mock
    SwitchDevicePortFindById switchDevicePortFindById;
    @Mock
    SwitchDevicePortDelete switchDevicePortDelete;
    @Mock
    SwitchDeviceOwnerValidator switchDeviceOwnerValidator;
    SwitchDeviceUseCaseDelete switchDeviceUseCaseDelete;

    @BeforeEach
    void init() {
        this.switchDeviceUseCaseDelete = new SwitchDeviceUseCaseDeleteImpl(
                switchDevicePortFindById,
                switchDevicePortDelete,
                switchDeviceOwnerValidator
        );
    }

    @Test
    void deleteSwitchDeviceTest() {
        //given
        final var switchDeviceId = 1L;
        final var switchDeviceToDelete = SwitchDevice.builder()
                .withId(switchDeviceId)
                .withLocationParent(LocationParent.builder().build())
                .build();
        //when
        when(switchDevicePortFindById.findSwitchDeviceById(anyLong())).thenReturn(Optional.of(switchDeviceToDelete));
        when(switchDeviceOwnerValidator.currentUserIsOwner(any())).thenReturn(true);
        switchDeviceUseCaseDelete.deleteSwitchDevice(switchDeviceId);
        //then
        verify(switchDevicePortDelete, times(1)).deleteSwitchDevice(switchDeviceToDelete);
    }


    @Test
    void deleteSwitchDeviceNotFoundTest() {
        //given
        final var switchDeviceId = 1L;
        when(switchDevicePortFindById.findSwitchDeviceById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> switchDeviceUseCaseDelete.deleteSwitchDevice(switchDeviceId));
    }


    @Test
    void deleteSwitchDeviceUserNotOwnerTest() {
        //given
        final var switchDeviceId = 1L;
        final var switchDeviceToDelete = SwitchDevice.builder()
                .withId(switchDeviceId)
                .withLocationParent(LocationParent.builder().build())
                .build();
        //when
        when(switchDevicePortFindById.findSwitchDeviceById(anyLong())).thenReturn(Optional.of(switchDeviceToDelete));
        when(switchDeviceOwnerValidator.currentUserIsOwner(any())).thenReturn(false);
        //then
        assertThrows(NotEnoughPrivilegesException.class, () -> switchDeviceUseCaseDelete.deleteSwitchDevice(switchDeviceId));
    }
}