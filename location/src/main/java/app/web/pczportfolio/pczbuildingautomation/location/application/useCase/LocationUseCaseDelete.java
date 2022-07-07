package app.web.pczportfolio.pczbuildingautomation.location.application.useCase;

public interface LocationUseCaseDelete {
    void deleteLocationById(long locationId);
    void deleteLocationsAccountRemoved(long accountId);
}
