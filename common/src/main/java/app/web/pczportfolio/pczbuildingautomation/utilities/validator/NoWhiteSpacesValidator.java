package app.web.pczportfolio.pczbuildingautomation.utilities.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoWhiteSpacesValidatorImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoWhiteSpacesValidator {
    String message() default "Field cannot include white spaces";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
