package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEventType;
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

    private String deviceName;

    private String deviceType;

    private String expectedState;

    private String state;

    @NotNull(message="Owner cannot be null")
    @NotBlank(message="Owner cannot be blank")
    private String owner;

    private boolean failed;

    @Enumerated(EnumType.STRING)
    private DeviceEventType deviceEventType;

}
