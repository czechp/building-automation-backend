package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

public interface DeviceChannelMsg {
    long getDeviceId();

    String getNewState();

    String getOwner();

    String getDeviceName();

    String getDeviceType();
}
