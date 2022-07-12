package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.service;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortDelete;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortFindById;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.useCase.DeviceEventUseCaseDelete;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceEventUseCaseDeleteImplTest {
    @Mock
    DeviceEventPortFindById deviceEventPortFindById;

    @Mock
    DeviceEventPortDelete deviceEventPortDelete;

    DeviceEventUseCaseDelete deviceEventUseCaseDelete;

    @BeforeEach
    void init() {
        this.deviceEventUseCaseDelete = new DeviceEventUseCaseDeleteImpl(deviceEventPortFindById, deviceEventPortDelete);
    }

    @Test
    void deleteDeviceEventTest() {
        //given
        final long deviceEventId = 1L;
        final var fetchedDeviceEvent = DeviceEvent.builder()
                .withId(deviceEventId)
                .build();
        //when
        when(deviceEventPortFindById.findDeviceEventById(anyLong())).thenReturn(Optional.of(fetchedDeviceEvent));
        final var removedDeviceEvent = deviceEventUseCaseDelete.deleteDeviceEvent(deviceEventId);
        //then
        verify(deviceEventPortDelete, times(1)).deleteDeviceEvent(any());
        assertNotNull(removedDeviceEvent);
    }

    @Test
    void deleteDeviceEventNotFoundTest() {
        //given
        final var deviceEventId = 1L;
        //when
        when(deviceEventPortFindById.findDeviceEventById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, ()-> deviceEventUseCaseDelete.deleteDeviceEvent(anyLong()));
    }
}