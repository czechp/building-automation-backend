package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

public interface DeviceChannel <T> {
    long getId();

    String getOwner();

    String getChannelRootName();

    String getName();

    T getDeviceState();

    T getDeviceExpectedState();

    String getDeviceTypeName();
}
