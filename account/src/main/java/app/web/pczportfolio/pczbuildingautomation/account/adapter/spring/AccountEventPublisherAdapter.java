package app.web.pczportfolio.pczbuildingautomation.account.adapter.spring;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortEmitDeleteEvent;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.account.event.AccountDeleteEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AccountEventPublisherAdapter implements AccountPortEmitDeleteEvent {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void emitAccountDeleteEvent(Account account) {
        AccountDeleteEvent accountDeleteEvent = new AccountDeleteEvent(this, account.getId());
        applicationEventPublisher.publishEvent(accountDeleteEvent);
    }
}
