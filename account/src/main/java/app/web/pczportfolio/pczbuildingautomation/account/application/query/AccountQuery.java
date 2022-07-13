package app.web.pczportfolio.pczbuildingautomation.account.application.query;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountQueryDto;
import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountUserDetailsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccountQuery {
    AccountQueryDto findAccountById(long accountId);

    List<AccountQueryDto> findAccountsAll(Pageable pageable);

}
