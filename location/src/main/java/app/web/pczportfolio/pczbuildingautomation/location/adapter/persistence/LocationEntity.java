package app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountSimpleEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "locations")
@Getter
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
@NoArgsConstructor
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Name of location cannot be null")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountSimpleEntity accountSimpleEntity;
}
