package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.EventType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "device_events")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class EventDeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long deviceId;

    @NotNull(message="Device name cannot be null")
    @NotBlank(message="Device name cannot be blank")
    private String deviceName;

    @NotNull(message="Device type cannot be null")
    @NotBlank(message="Device type cannot be blank")
    private String deviceType;

    @NotNull(message="Owner cannot be null")
    @NotBlank(message="Owner cannot be blank")
    private String owner;

    @NotNull(message="Content cannot be null")
    @NotBlank(message="Content cannot be blank")
    private String content;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

}
