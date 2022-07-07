package app.web.pczportfolio.pczbuildingautomation.location.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


public class LocationDeleteEvent extends ApplicationEvent {
    @Getter
    private long id;


    public LocationDeleteEvent(Object source, long id) {
        super(source);
        this.id = id;
    }
}
