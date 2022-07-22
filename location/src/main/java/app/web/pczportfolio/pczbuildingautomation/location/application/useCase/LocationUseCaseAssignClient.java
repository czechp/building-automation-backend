package app.web.pczportfolio.pczbuildingautomation.location.application.useCase;

import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;

public interface LocationUseCaseAssignClient {
    Location assignClient(long locationId, String clientName, String clientUUID);
    Location clearClient(long locationId);
}
