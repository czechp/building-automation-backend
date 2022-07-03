package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.location.dto.LocationSimpleEntity;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.LocationParent;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;

class SwitchDeviceEntityMapper {
    static SwitchDeviceEntity toEntity(SwitchDevice switchDevice) {
        return new SwitchDeviceEntity(
                switchDevice.getId(),
                switchDevice.getVersion(),
                switchDevice.getCreationTimestamp(),
                switchDevice.getLastFeedBackTimestamp(),
                switchDevice.getLastSetCommandTimestamp(),
                switchDevice.getName(),
                switchDevice.getOwner(),
                switchDevice.isExpectedState(),
                switchDevice.isState(),
                switchDevice.isDeviceError(),
                new LocationSimpleEntity(
                        switchDevice.getLocationParent().getId(),
                        switchDevice.getLocationParent().getName()
                )
        );
    }

    static SwitchDevice toDomain(SwitchDeviceEntity switchDeviceEntity) {
        return SwitchDevice.builder()
                .withId(switchDeviceEntity.getId())
                .withVersion(switchDeviceEntity.getVersion())
                .withCreationTimestamp(switchDeviceEntity.getCreationTimestamp())
                .withLastFeedBackTimestamp(switchDeviceEntity.getLastFeedbackTimeStamp())
                .withLastSetCommandTimestamp(switchDeviceEntity.getLastSetCommandTimestamp())
                .withName(switchDeviceEntity.getName())
                .withOwner(switchDeviceEntity.getOwner())
                .withExpectedState(switchDeviceEntity.isExpectedState())
                .withState(switchDeviceEntity.isState())
                .withDeviceError(switchDeviceEntity.isDeviceError())
                .withLocationParent(
                        LocationParent.builder()
                                .withId(switchDeviceEntity.getLocationSimpleEntity().getId())
                                .withName(switchDeviceEntity.getLocationSimpleEntity().getName())
                                .build()
                )
                .build();
    }
}
