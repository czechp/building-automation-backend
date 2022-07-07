package app.web.pczportfolio.pczbuildingautomation.account.application.port;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;

public interface AccountPortEmitDeleteEvent {
    void emitAccountDeleteEvent(Account account);
}
