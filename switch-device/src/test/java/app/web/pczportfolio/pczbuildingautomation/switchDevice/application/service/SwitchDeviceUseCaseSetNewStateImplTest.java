package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.exception.NotEnoughPrivilegesException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceSetNewStateDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortChannelSendMsg;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindById;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortSave;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseSetNewState;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.LocationParent;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SwitchDeviceUseCaseSetNewStateImplTest {
    @Mock
    SwitchDevicePortFindById switchDevicePortFindById;
    @Mock
    SwitchDeviceOwnerValidator switchDeviceOwnerValidator;
    @Mock
    SwitchDevicePortChannelSendMsg switchDevicePortChannelSendMsg;
    @Mock
    SwitchDevicePortSave switchDevicePortSave;

    SwitchDeviceUseCaseSetNewState switchDeviceUseCaseSetNewState;

    @BeforeEach
    void init() {
        this.switchDeviceUseCaseSetNewState = new SwitchDeviceUseCaseSetNewStateImpl(
                switchDevicePortFindById,
                switchDeviceOwnerValidator,
                switchDevicePortChannelSendMsg,
                switchDevicePortSave
        );
    }

    @Test
    void setNewStateForSwitchDeviceTest() throws JsonProcessingException {
        //given
        final var switchDeviceId = 1L;
        final var switchDeviceNewState = true;
        final var switchDeviceNewStateDto = new SwitchDeviceSetNewStateDto(switchDeviceId, switchDeviceNewState);
        final var fetchedSwitchDevice = SwitchDevice.builder()
                .withId(switchDeviceId)
                .withState(!switchDeviceNewState)
                .withExpectedState(!switchDeviceNewState)
                .withLocationParent(LocationParent.builder().build())
                .build();
        //when
        when(switchDevicePortFindById.findSwitchDeviceById(switchDeviceId)).thenReturn(Optional.of(fetchedSwitchDevice));
        final var switchDeviceWithNewState = switchDeviceUseCaseSetNewState.setNewStateForSwitchDevice(switchDeviceNewStateDto);
        //then
        assertEquals(switchDeviceNewState, switchDeviceWithNewState.isExpectedState());
        final var expectedSetCommandTimestamp = LocalDateTime.now().minusMinutes(1);
        assertTrue(switchDeviceWithNewState.getLastSetCommandTimestamp().isAfter(expectedSetCommandTimestamp));
        verify(switchDevicePortSave, times(1)).saveSwitchDevice(any());
        verify(switchDevicePortChannelSendMsg, times(1)).sendMsgToDeviceChannel(any(), any());
    }

    @Test
    void setNewStateForSwitchDeviceNotFountTest() throws JsonProcessingException {
        //given
        final var switchDeviceId = 1L;
        final var switchDeviceNewState = true;
        final var switchDeviceNewStateDto = new SwitchDeviceSetNewStateDto(switchDeviceId, switchDeviceNewState);
        //when
        when(switchDevicePortFindById.findSwitchDeviceById(switchDeviceId)).thenReturn(Optional.empty());
        final var switchDeviceWithNewState = switchDeviceUseCaseSetNewState.setNewStateForSwitchDevice(switchDeviceNewStateDto);
        //then
        assertThrows(NotFoundException.class, () -> switchDeviceUseCaseSetNewState.setNewStateForSwitchDevice(switchDeviceNewStateDto));
    }


    @Test
    void setNewStateForSwitchDeviceUserIsNotOwnerTest() throws JsonProcessingException {
        //given
        final var switchDeviceId = 1L;
        final var switchDeviceNewState = true;
        final var switchDeviceNewStateDto = new SwitchDeviceSetNewStateDto(switchDeviceId, switchDeviceNewState);
        final var fetchedSwitchDevice = SwitchDevice.builder()
                .withId(switchDeviceId)
                .withState(!switchDeviceNewState)
                .withExpectedState(!switchDeviceNewState)
                .withLocationParent(LocationParent.builder().build())
                .build();
        //when
        when(switchDevicePortFindById.findSwitchDeviceById(switchDeviceId)).thenReturn(Optional.of(fetchedSwitchDevice));
        doThrow(NotEnoughPrivilegesException.class).when(switchDeviceOwnerValidator).currentUserIsOwnerOrElseThrowException(any());
        //then
        assertThrows(NotEnoughPrivilegesException.class, () -> switchDeviceUseCaseSetNewState.setNewStateForSwitchDevice(switchDeviceNewStateDto));
    }
}