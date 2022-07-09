package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;

deviceEvent.getExpectedState(),
deviceEvent.getState(),
class DeviceEventEntityMapper {
    static DeviceEventEntity toEntity(DeviceEvent deviceEvent) {
        return new DeviceEventEntity(
                deviceEvent.getId(),
                deviceEvent.getVersion(),
                deviceEvent.getCreationTimestamp(),
                deviceEvent.getDeviceId(),
                deviceEvent.getDeviceName(),
                deviceEvent.getDeviceType(),
                deviceEvent.getExpectedState(),
                deviceEvent.getState(),
                deviceEvent.getOwner(),
                deviceEvent.getContent(),
                deviceEvent.getEventType()
        );
    }

    static DeviceEvent toDomain(DeviceEventEntity deviceEventEntity){
        return DeviceEvent.builder()
                .withId(deviceEventEntity.getId())
                .withVersion(deviceEventEntity.getVersion())
                .withCreationTimestamp(deviceEventEntity.getCreationTimestamp())
                .withDeviceId(deviceEventEntity.getDeviceId())
                .withDeviceName(deviceEventEntity.getDeviceName())
                .withDeviceType(deviceEventEntity.getDeviceType())
                .withExpectedState(deviceEventEntity.getExpectedState())
                .withState(deviceEventEntity.getState())
                .withOwner(deviceEventEntity.getOwner())
                .withContent(deviceEventEntity.getContent())
                .withEventType(deviceEventEntity.getEventType())
                .build();
    }
}
