package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDeviceConfiguration;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannelMsg;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class SwitchDeviceSetNewStateMsgDto implements DeviceChannelMsg {
    private long switchDeviceId;
    private String switchDeviceName;
    private String switchDeviceRootName;
    private String newState;
    private String owner;

    public SwitchDeviceSetNewStateMsgDto(SwitchDevice switchDevice) {
        this.switchDeviceId = switchDevice.getId();
        this.newState = String.valueOf(switchDevice.isExpectedState());
        this.owner = switchDevice.getOwner();
        this.switchDeviceName = switchDevice.getName();
        this.switchDeviceRootName = SwitchDeviceConfiguration.SWITCH_DEVICE_NAME;
    }

    @Override
    public long getDeviceId() {
        return this.switchDeviceId;
    }

    @Override
    public String getDeviceName() {
        return this.switchDeviceName;
    }

    @Override
    public String getDeviceType() {
        return this.switchDeviceRootName;
    }

    @Override
    public String getNewState() {
        return this.newState;
    }

    @Override
    public String getOwner() {
        return this.owner;
    }
}
