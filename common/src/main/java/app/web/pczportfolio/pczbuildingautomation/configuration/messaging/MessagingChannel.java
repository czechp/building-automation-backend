package app.web.pczportfolio.pczbuildingautomation.configuration.messaging;

public interface MessagingChannel {
    long getId();
    String getOwner();
    String getChannelRootName();
}
