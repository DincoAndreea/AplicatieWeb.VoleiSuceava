package ro.marcc.server.validators;

import ro.marcc.server.model.Meciuri.Echipa;
import ro.marcc.server.validators.interfaces.ConstrangereEchipeMeci;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidareContrangereEchipeMeci implements ConstraintValidator<ConstrangereEchipeMeci, Echipa[]> {
    @Override
    public void initialize(ConstrangereEchipeMeci constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Echipa[] echipe, ConstraintValidatorContext context) {
        return echipe!=null && echipe.length==2 && echipe[0]!=null && echipe[1]!=null;
    }
}
