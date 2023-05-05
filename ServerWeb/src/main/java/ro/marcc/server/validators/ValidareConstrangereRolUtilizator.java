package ro.marcc.server.validators;

import ro.marcc.server.validators.interfaces.ConstrangereRolUtilizator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidareConstrangereRolUtilizator implements ConstraintValidator<ConstrangereRolUtilizator, Character> {
    @Override
    public void initialize(ConstrangereRolUtilizator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Character rol, ConstraintValidatorContext context) {
        return rol!=null && (rol=='v'||rol=='c'||rol=='a');
    }
}
