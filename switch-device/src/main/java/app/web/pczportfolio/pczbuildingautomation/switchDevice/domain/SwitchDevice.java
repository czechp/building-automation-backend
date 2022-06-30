package app.web.pczportfolio.pczbuildingautomation.switchDevice.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(setterPrefix = "with")
public class SwitchDevice {
    private long id;

    private long version;

    private LocalDateTime creationTimestamp;

    private String name;

    private boolean expectedState;

    private boolean state;

    private boolean deviceError;

    private LocationParent locationParent;
}
