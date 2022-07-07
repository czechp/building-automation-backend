package app.web.pczportfolio.pczbuildingautomation.location.adapter.spring;

import app.web.pczportfolio.pczbuildingautomation.account.event.AccountDeleteEvent;
import app.web.pczportfolio.pczbuildingautomation.location.application.useCase.LocationUseCaseDelete;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class LocationEventConsumerAdapter implements ApplicationListener<AccountDeleteEvent> {
    private final LocationUseCaseDelete locationUseCaseDelete;

    @Override
    public void onApplicationEvent(AccountDeleteEvent accountDeleteEvent) {
        locationUseCaseDelete.deleteLocationsAccountRemoved(accountDeleteEvent.getId());
    }
}
