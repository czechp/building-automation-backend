package app.web.pczportfolio.pczbuildingautomation.account.application.query;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountQueryDto;

import java.util.List;
import java.util.Optional;

public interface AccountQuery {
    Optional<AccountQueryDto> findAccountById(long accountId);
    List<AccountQueryDto> findAccountsAll();
}
