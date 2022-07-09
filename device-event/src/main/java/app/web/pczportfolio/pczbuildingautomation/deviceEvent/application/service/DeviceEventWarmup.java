package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.service;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortSave;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.EventType;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannel;
import app.web.pczportfolio.pczbuildingautomation.utilities.tools.LoggerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@Profile({"development", "test"})
class DeviceEventWarmup {
    private static final String EVENT_OWNER = "user";
    private final DeviceEventPortSave deviceEventPortSave;
    private final Logger logger = LoggerFactory.getLogger(DeviceEventWarmup.class);

    DeviceEventWarmup(DeviceEventPortSave deviceEventPortSave) {
        this.deviceEventPortSave = deviceEventPortSave;
    }

    @EventListener(ApplicationReadyEvent.class)
    void warmup() {
        LoggerInfo.showInfo(logger, "Warmup for device event entity");
        saveNormalEvents();
        saveFailedEvents();
    }

    private void saveNormalEvents() {
        Stream.of(
                DeviceEvent.createEvent(supplyDeviceChannel(1, "First device event"), EventType.CREATE),
                DeviceEvent.createEvent(supplyDeviceChannel(2, "Second device event"), EventType.DELETE),
                DeviceEvent.createEvent(supplyDeviceChannel(3, "Third device event"), EventType.NEW_STATE_REQUEST),
                DeviceEvent.createEvent(supplyDeviceChannel(4, "Fourth device event"), EventType.FEEDBACK_FROM_DEVICE)
        ).forEach(deviceEventPortSave::save);
    }

    private DeviceChannel supplyDeviceChannel(long id, String name) {
        return new DeviceChannel() {
            @Override
            public long getId() {
                return id;
            }

            @Override
            public String getOwner() {
                return EVENT_OWNER;
            }

            @Override
            public String getChannelRootName() {
                return "Switch device";
            }

            @Override
            public String getName() {
                return name;
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
                return "switch-device";
            }
        };
    }

    private void saveFailedEvents() {
        Stream.of(
                        DeviceEvent.createFailedEvent(EVENT_OWNER, EventType.CREATE),
                        DeviceEvent.createFailedEvent(EVENT_OWNER, EventType.DELETE),
                        DeviceEvent.createFailedEvent(EVENT_OWNER, EventType.NEW_STATE_REQUEST),
                        DeviceEvent.createFailedEvent(EVENT_OWNER, EventType.FEEDBACK_FROM_DEVICE)
                )
                .forEach(deviceEventPortSave::save);
    }
}
