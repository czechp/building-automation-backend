package app.web.pczportfolio.pczbuildingautomation.location.application.port;

import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;

import java.util.List;

public interface LocationPortFindByAccountId {
    List<Location> findLocationsByAccountId(long accountId);
}
