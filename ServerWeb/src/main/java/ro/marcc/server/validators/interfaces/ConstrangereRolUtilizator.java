package ro.marcc.server.validators.interfaces;

import ro.marcc.server.validators.ValidareConstrangereRolUtilizator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidareConstrangereRolUtilizator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD})
public @interface ConstrangereRolUtilizator {
    String message() default "Rol invalid! Rolurile ce pot fi folosite sunt v, c sau a.";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default{};
}
