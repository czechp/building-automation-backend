package app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
class DeviceEvent {
    private long id;
    private long deviceId;
    private String deviceName;
    private String deviceType;
    private String owner;
    private String content;
    private EventType eventType;
}
