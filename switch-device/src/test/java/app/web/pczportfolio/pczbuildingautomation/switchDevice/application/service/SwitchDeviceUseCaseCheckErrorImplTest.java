package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindById;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortSave;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseCheckError;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.LocationParent;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SwitchDeviceUseCaseCheckErrorImplTest {
    @Mock
    SwitchDevicePortFindById switchDevicePortFindById;
    @Mock
    SwitchDevicePortSave switchDevicePortSave;

    SwitchDeviceUseCaseCheckError switchDeviceUseCaseCheckError;

    @BeforeEach
    void init() {
        this.switchDeviceUseCaseCheckError = new SwitchDeviceUseCaseCheckErrorImpl(switchDevicePortFindById, switchDevicePortSave);
    }

    @Test
    void checkSwitchDeviceErrorNotInRangeTest(){
        //given
        final var expectedState = false;
        final var state = true;
        final var switchDeviceId = 1L;
        final var fetchedSwitchDevice = SwitchDevice.builder()
                .withId(switchDeviceId)
                .withExpectedState(expectedState)
                .withState(state)
                .withLastSetCommandTimestamp(LocalDateTime.now().minusMinutes(2))
                .withLocationParent(new LocationParent())
                .build();
        //when
        when(switchDevicePortFindById.findSwitchDeviceById(switchDeviceId)).thenReturn(Optional.of(fetchedSwitchDevice));
        final var switchDevice = switchDeviceUseCaseCheckError.checkSwitchDeviceError(switchDeviceId);
        //then
        verify(switchDevicePortSave).saveSwitchDevice(fetchedSwitchDevice);
        assertTrue(switchDevice.isDeviceError());
    }


    @Test
    void checkSwitchDeviceErrorInRangeTest(){
        //given
        final var expectedState = false;
        final var state = true;
        final var switchDeviceId = 1L;
        final var fetchedSwitchDevice = SwitchDevice.builder()
                .withId(switchDeviceId)
                .withExpectedState(expectedState)
                .withState(state)
                .withLastSetCommandTimestamp(LocalDateTime.now().minusSeconds(30))
                .withLocationParent(new LocationParent())
                .build();
        //when
        when(switchDevicePortFindById.findSwitchDeviceById(switchDeviceId)).thenReturn(Optional.of(fetchedSwitchDevice));
        final var switchDevice = switchDeviceUseCaseCheckError.checkSwitchDeviceError(switchDeviceId);
        //then
        verify(switchDevicePortSave).saveSwitchDevice(fetchedSwitchDevice);
        assertFalse(switchDevice.isDeviceError());
    }


    @Test
    void checkSwitchDeviceErrorStatesEqualTest(){
        //given
        final var expectedState = true;
        final var state = true;
        final var switchDeviceId = 1L;
        final var fetchedSwitchDevice = SwitchDevice.builder()
                .withId(switchDeviceId)
                .withExpectedState(expectedState)
                .withState(state)
                .withLastSetCommandTimestamp(LocalDateTime.now().minusMinutes(2))
                .withLocationParent(new LocationParent())
                .build();
        //when
        when(switchDevicePortFindById.findSwitchDeviceById(switchDeviceId)).thenReturn(Optional.of(fetchedSwitchDevice));
        final var switchDevice = switchDeviceUseCaseCheckError.checkSwitchDeviceError(switchDeviceId);
        //then
        verify(switchDevicePortSave).saveSwitchDevice(fetchedSwitchDevice);
        assertFalse(switchDevice.isDeviceError());
    }


}