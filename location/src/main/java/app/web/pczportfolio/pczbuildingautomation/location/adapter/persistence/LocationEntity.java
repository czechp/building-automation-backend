package app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountSimpleEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "locations")
@Getter
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private long version;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @NotNull(message = "Name of location cannot be null")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountSimpleEntity accountSimpleEntity;
}
