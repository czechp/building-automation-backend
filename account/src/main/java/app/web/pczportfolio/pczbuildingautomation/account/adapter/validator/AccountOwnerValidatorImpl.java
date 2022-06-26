package app.web.pczportfolio.pczbuildingautomation.account.adapter.validator;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindById;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindByUsername;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityCurrentUser;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.util.Optional;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
class AccountOwnerValidatorImpl implements ConstraintValidator<AccountOwnerValidator, Object[]> {

    private final AccountPortFindById accountPortFindById;
    private final SecurityCurrentUser securityCurrentUser;
    private final AccountPortFindByUsername accountPortFindByUsername;

    AccountOwnerValidatorImpl(AccountPortFindById accountPortFindById, SecurityCurrentUser securityCurrentUser, AccountPortFindByUsername accountPortFindByUsername) {
        this.accountPortFindById = accountPortFindById;
        this.securityCurrentUser = securityCurrentUser;
        this.accountPortFindByUsername = accountPortFindByUsername;
    }

    @Override
    public void initialize(AccountOwnerValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object[] parameters, ConstraintValidatorContext constraintValidatorContext) {
        var isValidated = false;
        final long accountId = fetchAccountIdFromParameters(parameters);
        return accountPortFindById.findAccountById(accountId)
                .filter(this::validateOwning)
                .isPresent();
    }

    private boolean validateOwning(Account account) {
        String userMakingRequest = securityCurrentUser.getCurrentUser();
        Account accountOfCurrentUser = accountPortFindByUsername.findAccountByUsername(userMakingRequest)
                .orElseThrow(() -> new NotFoundException("There is no account with username: " + userMakingRequest));
        return accountOfCurrentUser.getAccountRole().equals(AccountRole.ADMIN)
                || account.getUsername().equals(userMakingRequest);
    }

    private long fetchAccountIdFromParameters(Object[] parameters) {
        return Optional.ofNullable(parameters[0])
                .filter(p -> p instanceof Long)
                .map(p -> (long) p)
                .orElse(0L);
    }
}
