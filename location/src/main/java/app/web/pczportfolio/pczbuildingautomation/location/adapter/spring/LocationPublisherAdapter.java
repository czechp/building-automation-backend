package app.web.pczportfolio.pczbuildingautomation.location.adapter.spring;

import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortEmitEventDelete;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import app.web.pczportfolio.pczbuildingautomation.location.event.LocationDeleteEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class LocationPublisherAdapter implements LocationPortEmitEventDelete {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void emitLocationDeleteEvent(Location location) {
        final var locationDeleteEvent = new LocationDeleteEvent(this, location.getId());
        applicationEventPublisher.publishEvent(locationDeleteEvent);
    }
}
