package com.n26.transaction.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
@Constraint(validatedBy = TimeStampValidator.class)
public @interface TimeStampConstraint {
	String message() default "Invalid timestamp";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
