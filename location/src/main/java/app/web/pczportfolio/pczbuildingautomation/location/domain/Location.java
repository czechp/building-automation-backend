package app.web.pczportfolio.pczbuildingautomation.location.domain;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiledException;
import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationCreateCommandDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Location {
    private long id;

    private long version;

    private String name;

    private LocalDateTime creationTimestamp;
    private AccountParent accountParent;

    private String clientUUID;

    private String clientName;

    public Location(String name, AccountParent accountParent) {
        this.name = name;
        this.accountParent = accountParent;
    }

    public static Location create(LocationCreateCommandDto locationCommandDto, AccountFacadeDto accountFacadeDto) {
        return new Location(
                locationCommandDto.getName(),
                new AccountParent(accountFacadeDto.getId(), accountFacadeDto.getUsername())
        );
    }

    public void assignClient(String clientUUID, String clientName) {
        final var dataCorrect = !clientUUID.isBlank() && !clientName.isBlank();
        if (dataCorrect) {
            this.clientName = clientName;
            this.clientUUID = clientUUID;
        } else throw new ConditionsNotFulFiledException("Client name or client UUID is not correct");
    }

    public void clearClientBinding() {
        this.clientUUID = "";
        this.clientName = "";
    }
}
