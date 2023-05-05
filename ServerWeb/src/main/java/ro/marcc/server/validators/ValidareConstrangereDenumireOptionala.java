package ro.marcc.server.validators;

import ro.marcc.server.validators.interfaces.ConstrangereDenumireOptionala;
import ro.marcc.server.validators.interfaces.ConstrangereLinkuriMedia;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidareConstrangereDenumireOptionala implements ConstraintValidator<ConstrangereDenumireOptionala, String> {
    @Override
    public void initialize(ConstrangereDenumireOptionala constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value==null || value.length()>2;
    }
}
