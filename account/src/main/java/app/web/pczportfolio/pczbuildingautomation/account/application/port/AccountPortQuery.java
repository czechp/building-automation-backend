package app.web.pczportfolio.pczbuildingautomation.account.application.port;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountQueryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccountPortQuery {
    List<AccountQueryDto> findAllAccountQuery(Pageable pageable);

    Optional<AccountQueryDto> findAccountByIdQuery(long accountId);
}
