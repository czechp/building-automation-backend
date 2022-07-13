package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.messaging;

import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannelMsg;
import lombok.NoArgsConstructor;

@NoArgsConstructor
class MessageFromDeviceDlx implements DeviceChannelMsg {
    private long deviceId;
    private String newState;
    private String owner;
    private String deviceName;
    private String deviceType;

    @Override
    public long getDeviceId() {
        return deviceId;
    }

    @Override
    public String getNewState() {
        return newState;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public String getDeviceName() {
        return deviceName;
    }

    @Override
    public String getDeviceType() {
        return deviceType;
    }
}
