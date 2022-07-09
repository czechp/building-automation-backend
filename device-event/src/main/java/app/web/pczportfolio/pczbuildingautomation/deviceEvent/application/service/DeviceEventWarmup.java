package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@Profile({"development", "test"})
class DeviceEventWarmup {

    @EventListener(ApplicationReadyEvent.class)
    void warmup() {

    }

}
