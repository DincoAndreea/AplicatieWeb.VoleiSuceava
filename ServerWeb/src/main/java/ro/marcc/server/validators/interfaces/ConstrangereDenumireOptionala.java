package ro.marcc.server.validators.interfaces;


import ro.marcc.server.validators.ValidareConstrangereDenumireOptionala;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidareConstrangereDenumireOptionala.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD})
public @interface ConstrangereDenumireOptionala {
    String message() default "Campul trebuie sa fie ori null ori sa aiba lungimea minima de 3 caractere!";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default{};
}
