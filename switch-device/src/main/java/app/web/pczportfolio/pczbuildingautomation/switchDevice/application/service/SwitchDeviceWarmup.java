package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.location.dto.LocationFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.messaging.SwitchDeviceMsgAdapterInitializer;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortCreateChannel;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindLocationById;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortSave;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.LocationParent;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import app.web.pczportfolio.pczbuildingautomation.utilities.LoggerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@Service
@Profile({"development", "test"})
class SwitchDeviceWarmup {
    private final SwitchDevicePortFindLocationById switchDevicePortFindLocationById;
    private final SwitchDevicePortSave switchDevicePortSave;

    private final SwitchDevicePortCreateChannel switchDevicePortCreateChannel;
    private final SwitchDeviceMsgAdapterInitializer switchDeviceMsgAdapterInitializer;
    private final Logger logger = LoggerFactory.getLogger(SwitchDeviceWarmup.class);

    public SwitchDeviceWarmup(SwitchDevicePortFindLocationById switchDevicePortFindLocationById, SwitchDevicePortSave switchDevicePortSave, SwitchDevicePortCreateChannel switchDevicePortCreateChannel, SwitchDeviceMsgAdapterInitializer switchDeviceMsgAdapterInitializer) {
        this.switchDevicePortFindLocationById = switchDevicePortFindLocationById;
        this.switchDevicePortSave = switchDevicePortSave;
        this.switchDevicePortCreateChannel = switchDevicePortCreateChannel;
        this.switchDeviceMsgAdapterInitializer = switchDeviceMsgAdapterInitializer;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        LoggerInfo.showInfo(logger, "Warmup for Switch Device Entity");
        LocationFacadeDto locationParent = switchDevicePortFindLocationById.findLocationById(1L)
                .orElseThrow(() -> new RuntimeException("Cannot making warmup for SWITCH DEVICE ENTITY"));

        Stream.of(
                        SwitchDevice.builder()
                                .withName("Switch device 1")
                                .withLastSetCommandTimestamp(LocalDateTime.now())
                                .withLastFeedBackTimestamp(LocalDateTime.now())
                                .withOwner(locationParent.getOwnerUsername())
                                .withLocationParent(LocationParent.builder().withId(locationParent.getId()).withName(locationParent.getName()).build())
                                .build(),
                        SwitchDevice.builder()
                                .withName("Switch device 2")
                                .withLastSetCommandTimestamp(LocalDateTime.now())
                                .withLastFeedBackTimestamp(LocalDateTime.now())
                                .withOwner(locationParent.getOwnerUsername())
                                .withLocationParent(LocationParent.builder().withId(locationParent.getId()).withName(locationParent.getName()).build())
                                .build(),
                        SwitchDevice.builder()
                                .withName("Switch device 3")
                                .withLastSetCommandTimestamp(LocalDateTime.now())
                                .withLastFeedBackTimestamp(LocalDateTime.now())
                                .withOwner(locationParent.getOwnerUsername())
                                .withLocationParent(LocationParent.builder().withId(locationParent.getId()).withName(locationParent.getName()).build())
                                .build()
                ).map(switchDevicePortSave::saveSwitchDevice)
                .forEach(switchDevicePortCreateChannel::createChannelForSwitchDevice);

    }
}
