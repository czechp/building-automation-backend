package app.web.pczportfolio.pczbuildingautomation.account.application.query;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountQueryDto;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindAll;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindById;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class AccountQueryImpl implements AccountQuery {
    private final AccountPortFindById accountPortFindById;
    private final AccountPortFindAll accountPortFindAll;

    @Override
    public Optional<AccountQueryDto> findAccountById(long accountId) {
        return accountPortFindById.findAccountById(accountId)
                .map(AccountQueryDto::toAccountQueryDto);
    }

    @Override
    public List<AccountQueryDto> findAccountsAll() {
        return accountPortFindAll.findAllAccounts()
                .stream()
                .map(AccountQueryDto::toAccountQueryDto)
                .collect(Collectors.toList());
    }
}
