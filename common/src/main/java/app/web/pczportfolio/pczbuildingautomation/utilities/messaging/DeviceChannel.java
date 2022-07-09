package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

public interface DeviceChannel  {
    long getId();

    String getOwner();

    String getChannelRootName();

    String getName();

    String getEventState();

    String getEventExpectState();

    String getDeviceTypeName();
}
