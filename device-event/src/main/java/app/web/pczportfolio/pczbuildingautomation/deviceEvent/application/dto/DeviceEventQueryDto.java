package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.dto;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEventType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeviceEventQueryDto {
    private long id;
    private LocalDateTime creationTimeStamp;
    private long deviceId;
    private boolean failed;
    private DeviceEventType deviceEvent;
    private String content;

    public static DeviceEventQueryDto create(DeviceEvent deviceEvent, String content) {
        return new DeviceEventQueryDto(
                deviceEvent.getId(),
                deviceEvent.getCreationTimestamp(),
                deviceEvent.getDeviceId(),
                deviceEvent.isFailed(),
                deviceEvent.getDeviceEventType(),
                content
        );
    }
}


