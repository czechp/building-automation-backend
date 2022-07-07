package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.*;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SwitchDeviceUseCaseDeleteImplTest {
    @Mock
    SwitchDevicePortFindById switchDevicePortFindById;

    @Mock
    SwitchDevicePortFindByLocationId switchDevicePortFindByLocationId;

    @Mock
    SwitchDeviceOwnerValidator switchDeviceOwnerValidator;
    @Mock
    SwitchDevicePortFindByOwner switchDevicePortFindByOwner;
    @Mock
    SwitchDevicePortDelete switchDevicePortDelete;
    @Mock
    SwitchDevicePortDeleteChannel switchDevicePortDeleteChannel;
    SwitchDeviceUseCaseDelete switchDeviceUseCaseDelete;

    @BeforeEach
    void init() {
        this.switchDeviceUseCaseDelete = new SwitchDeviceUseCaseDeleteImpl(
                switchDevicePortFindById,
                switchDevicePortFindByLocationId,
                switchDevicePortFindByOwner,
                switchDeviceOwnerValidator,
                switchDevicePortDelete,
                switchDevicePortDeleteChannel
        );
    }

    @Test
    void deleteSwitchDeviceByIdTest() {
        //given
        final var switchDeviceId = 1L;
        final var switchDeviceToDelete = SwitchDevice.builder()
                .withId(switchDeviceId)
                .withLocationParent(LocationParent.builder().build())
                .build();
        //when
        when(switchDevicePortFindById.findSwitchDeviceById(anyLong())).thenReturn(Optional.of(switchDeviceToDelete));
        switchDeviceUseCaseDelete.deleteSwitchDeviceById(switchDeviceId);
        //then
        verify(switchDevicePortDelete, times(1)).deleteSwitchDevice(switchDeviceToDelete);
    }


    @Test
    void deleteSwitchDeviceByIdNotFoundTest() {
        //given
        final var switchDeviceId = 1L;
        when(switchDevicePortFindById.findSwitchDeviceById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> switchDeviceUseCaseDelete.deleteSwitchDeviceById(switchDeviceId));
    }


}