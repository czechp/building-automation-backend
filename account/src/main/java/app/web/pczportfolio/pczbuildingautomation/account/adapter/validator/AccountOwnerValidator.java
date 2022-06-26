package app.web.pczportfolio.pczbuildingautomation.account.adapter.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AccountOwnerValidatorImpl.class)
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccountOwnerValidator {
    String message() default "You are not owner of account";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
