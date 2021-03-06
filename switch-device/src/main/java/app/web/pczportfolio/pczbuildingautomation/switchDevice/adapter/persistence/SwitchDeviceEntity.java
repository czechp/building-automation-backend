package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.location.dto.LocationSimpleEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "switch_devices")
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(setterPrefix = "with")
public class SwitchDeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private long version;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @NotNull(message = "Date of last feedback cannot be null")
    private LocalDateTime lastFeedbackTimeStamp;


    @NotNull(message = "Date of last state command cannot be null")
    private LocalDateTime lastSetCommandTimestamp;

    @NotNull(message = "Name of switch device cannot be null")
    @Length(min = 2, max = 200, message = "Name of switch device has to have length between 2 and 200 characters")
    private String name;


    @NotNull(message = "Owner of switch device cannot be null")
    @Length(min = 2, max = 200, message = "Owner of switch device has to have length between 2 and 200 characters")
    private String owner;

    private boolean expectedState;

    private boolean state;

    private boolean deviceError;

    @ManyToOne(fetch = FetchType.LAZY)
    private LocationSimpleEntity locationSimpleEntity;

}
