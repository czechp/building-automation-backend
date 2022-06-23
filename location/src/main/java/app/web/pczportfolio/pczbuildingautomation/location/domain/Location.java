package app.web.pczportfolio.pczbuildingautomation.location.domain;

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
}
