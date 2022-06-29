package app.web.pczportfolio.pczbuildingautomation.switchDevice.domain;


import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(setterPrefix = "with")
class LocationParent {
    private long id;
    private String name;
}
