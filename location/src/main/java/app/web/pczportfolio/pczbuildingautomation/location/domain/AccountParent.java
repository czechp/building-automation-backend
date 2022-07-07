package app.web.pczportfolio.pczbuildingautomation.location.domain;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class AccountParent {
    private long id;
    private String username;
}
