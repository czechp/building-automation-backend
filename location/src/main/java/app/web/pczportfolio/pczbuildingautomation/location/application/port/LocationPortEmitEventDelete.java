package app.web.pczportfolio.pczbuildingautomation.location.application.port;

import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;

public interface LocationPortEmitEventDelete {
    void emitLocationDeleteEvent(Location location);
}
