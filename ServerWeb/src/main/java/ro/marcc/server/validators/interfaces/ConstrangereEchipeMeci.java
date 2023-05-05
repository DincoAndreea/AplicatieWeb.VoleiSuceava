package ro.marcc.server.validators.interfaces;

import ro.marcc.server.validators.ValidareContrangereEchipeMeci;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidareContrangereEchipeMeci.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD})
public @interface ConstrangereEchipeMeci {
    String message() default "Echipe invalide! Trebuie folosit un tablou de 2 echipe care sa nu fie null!.";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default{};
}
