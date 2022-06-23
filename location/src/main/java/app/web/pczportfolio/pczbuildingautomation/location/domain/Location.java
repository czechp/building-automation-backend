package app.web.pczportfolio.pczbuildingautomation.location.domain;

import app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence.LocationEntity;
import lombok.*;

@Getter
@Setter
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Location {
    private long id;
    private String name;
    private AccountParent accountParent;

    public static Location mapFromEntity(LocationEntity locationEntity) {
        return Location.builder()
                .withId(locationEntity.getId())
                .withName(locationEntity.getName())
                .withAccountParent(
                        AccountParent.builder()
                                .withId(locationEntity.getAccountSimpleEntity().getId())
                                .withUsername(locationEntity.getAccountSimpleEntity().getUsername())
                                .build())
                .build();
    }
}
