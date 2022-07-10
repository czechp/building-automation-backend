package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.service;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortFindCurrentUser;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortSave;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.useCase.DeviceEventUseCaseCreate;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEventType;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceEventUseCaseCreateImplTest {
    @Mock
    DeviceEventPortSave deviceEventPortSave;
    @Mock
    DeviceEventPortFindCurrentUser deviceEventPortFindCurrentUser;
    DeviceEventUseCaseCreate deviceEventUseCaseCreate;

    @BeforeEach()
    void init() {
        this.deviceEventUseCaseCreate = new DeviceEventUseCaseCreateImpl(
                deviceEventPortSave,
                deviceEventPortFindCurrentUser
        );
    }

    ;

    @Test
    void createDeviceEvent() {
        //given
        final var deviceChannel = supplyDeviceChannel();
        final var eventType = DeviceEventType.CREATE;
        //when
        final var newCreateDeviceEvent = deviceEventUseCaseCreate.createDeviceEvent(deviceChannel, eventType);
        //then
        verify(deviceEventPortSave, times(1)).save(any());

        assertEquals(deviceChannel.getName(), newCreateDeviceEvent.getDeviceName());
        assertEquals(deviceChannel.getDeviceTypeName(), newCreateDeviceEvent.getDeviceType());
        assertEquals(deviceChannel.getOwner(), newCreateDeviceEvent.getOwner());
        assertEquals(deviceChannel.getEventExpectState(), newCreateDeviceEvent.getExpectedState());
        assertEquals(deviceChannel.getEventState(), newCreateDeviceEvent.getState());
        assertEquals(eventType, newCreateDeviceEvent.getDeviceEventType());
        assertFalse(newCreateDeviceEvent.isFailed());
    }

    @Test
    void createDeviceEventFailed() {
        //given
        final var eventType = DeviceEventType.DELETE;
        final var user = "user";
        //when
        when(deviceEventPortFindCurrentUser.findCurrentUser()).thenReturn(user);
        final var newCreatedFailedDeviceEvent = deviceEventUseCaseCreate.createDeviceEventFailed(eventType);
        //then
        assertTrue(newCreatedFailedDeviceEvent.isFailed());
        assertEquals(eventType, newCreatedFailedDeviceEvent.getDeviceEventType());
        assertEquals(user, newCreatedFailedDeviceEvent.getOwner());
    }

    DeviceChannel supplyDeviceChannel() {
        return new DeviceChannel() {
            @Override
            public long getId() {
                return 100L;
            }

            @Override
            public String getOwner() {
                return "Some owner";
            }

            @Override
            public String getChannelRootName() {
                return "some-root-name";
            }

            @Override
            public String getName() {
                return "Some name";
            }

            @Override
            public String getEventState() {
                return "OFF";
            }

            @Override
            public String getEventExpectState() {
                return "OFF";
            }

            @Override
            public String getDeviceTypeName() {
                return "Switch device";
            }
        };
    }
}