package app.web.pczportfolio.pczbuildingautomation.account.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class AccountDeleteEvent extends ApplicationEvent {
    @Getter
    private long id;

    public AccountDeleteEvent(Object source, long id) {
        super(source);
        this.id = id;
    }
}
