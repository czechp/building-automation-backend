package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.EventType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "device_events")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class DeviceEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private long version;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

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
