package app.web.pczportfolio.pczbuildingautomation.account.application.query;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountQueryDto;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortQuery;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class AccountQueryImpl implements AccountQuery {
    private final AccountPortQuery accountPortQuery;

    @Override
    public AccountQueryDto findAccountById(long accountId) {
        //TODO: check owning
        return accountPortQuery.findAccountByIdQuery(accountId)
                .orElseThrow(() -> new NotFoundException("There is no account with id:" + accountId));
    }

    @Override
    public List<AccountQueryDto> findAccountsAll(Pageable pageable) {
        return accountPortQuery.findAllAccountQuery(pageable);
    }
}
