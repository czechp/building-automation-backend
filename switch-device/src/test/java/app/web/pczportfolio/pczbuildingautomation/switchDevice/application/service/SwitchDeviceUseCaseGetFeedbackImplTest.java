package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiledException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotEnoughPrivilegesException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceFeedbackDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindById;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortSave;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseGetFeedback;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SwitchDeviceUseCaseGetFeedbackImplTest {
    @Mock
    SwitchDevicePortFindById switchDevicePortFindById;
    @Mock
    SwitchDeviceOwnerValidator switchDeviceOwnerValidator;
    @Mock
    SwitchDevicePortSave switchDevicePortSave;
    SwitchDeviceUseCaseGetFeedback switchDeviceUseCaseGetFeedback;

    @BeforeEach
    void init() {
        this.switchDeviceUseCaseGetFeedback = new SwitchDeviceUseCaseGetFeedbackImpl(
                switchDevicePortFindById,
                switchDeviceOwnerValidator,
                switchDevicePortSave
        );
    }

    @Test
    void receiveFeedbackFromDeviceTest() {
        //given
        final var switchDeviceId = 1L;
        final var switchDeviceNewState = true;
        final var dtoWithFeedbackInfo = new SwitchDeviceFeedbackDto(switchDeviceId, switchDeviceNewState);
        final var fetchedSwitchDevice = SwitchDevice.builder()
                .withId(switchDeviceId)
                .withExpectedState(switchDeviceNewState)
                .withState(!switchDeviceNewState)
                .withDeviceError(true)
                .build();
        //when
        when(switchDevicePortFindById.findSwitchDeviceById(switchDeviceId)).thenReturn(Optional.of(fetchedSwitchDevice));
        final var switchDeviceAfterFeedback = switchDeviceUseCaseGetFeedback.receiveFeedbackFromDevice(dtoWithFeedbackInfo);
        //then
        verify(switchDevicePortSave, times(1)).saveSwitchDevice(switchDeviceAfterFeedback);
        assertEquals(switchDeviceNewState, switchDeviceAfterFeedback.isState());
        assertFalse(switchDeviceAfterFeedback.isDeviceError());
    }

    @Test
    void receiveFeedbackFromDeviceNotFoundTest() {
        //given
        final var switchDeviceId = 1L;
        final var switchDeviceNewState = true;
        final var dtoWithFeedbackInfo = new SwitchDeviceFeedbackDto(switchDeviceId, switchDeviceNewState);
        //when
        when(switchDevicePortFindById.findSwitchDeviceById(switchDeviceId)).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> switchDeviceUseCaseGetFeedback.receiveFeedbackFromDevice(dtoWithFeedbackInfo));
    }

    @Test
    void receiveFeedbackFromDeviceUserIsNotOwnerTest() {
        //given
        final var switchDeviceId = 1L;
        final var switchDeviceNewState = true;
        final var dtoWithFeedbackInfo = new SwitchDeviceFeedbackDto(switchDeviceId, switchDeviceNewState);
        final var fetchedSwitchDevice = SwitchDevice.builder()
                .withId(switchDeviceId)
                .withState(!switchDeviceNewState)
                .withDeviceError(true)
                .build();
        //when
        when(switchDevicePortFindById.findSwitchDeviceById(switchDeviceId)).thenReturn(Optional.of(fetchedSwitchDevice));
        doThrow(NotEnoughPrivilegesException.class)
                .when(switchDeviceOwnerValidator)
                .currentUserIsOwnerOrElseThrowException(fetchedSwitchDevice);
        //then
        assertThrows(NotEnoughPrivilegesException.class, () -> switchDeviceUseCaseGetFeedback.receiveFeedbackFromDevice(dtoWithFeedbackInfo));
    }

    @Test
    void receiveFeedbackFromDeviceStateDoesNotMatchTest() {
        //given
        final var switchDeviceId = 1L;
        final var switchDeviceNewState = true;
        final var dtoWithFeedbackInfo = new SwitchDeviceFeedbackDto(switchDeviceId, switchDeviceNewState);
        final var fetchedSwitchDevice = SwitchDevice.builder()
                .withId(switchDeviceId)
                .withExpectedState(!switchDeviceNewState)
                .withDeviceError(true)
                .build();
        //when
        when(switchDevicePortFindById.findSwitchDeviceById(switchDeviceId)).thenReturn(Optional.of(fetchedSwitchDevice));
        //then
        assertThrows(ConditionsNotFulFiledException.class, () -> switchDeviceUseCaseGetFeedback.receiveFeedbackFromDevice(dtoWithFeedbackInfo));
    }

}