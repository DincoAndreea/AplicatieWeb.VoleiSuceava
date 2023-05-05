package ro.marcc.server.validators.interfaces;


import ro.marcc.server.validators.ValidareConstrangereLinkuriMedia;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidareConstrangereLinkuriMedia.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD})
public @interface ConstrangereLinkuriMedia {
    String message() default "Lista invalida! Poate fi stocata o lista de maxim 10 linkuri.";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default{};
}
