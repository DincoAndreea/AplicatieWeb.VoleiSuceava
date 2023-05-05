package ro.marcc.server.validators;

import ro.marcc.server.validators.interfaces.ConstrangereLinkuriMedia;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidareConstrangereLinkuriMedia implements ConstraintValidator<ConstrangereLinkuriMedia, List<String>> {

    @Override
    public void initialize(ConstrangereLinkuriMedia constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<String> linkuri, ConstraintValidatorContext context) {
        return linkuri!=null&&linkuri.size()<11;
    }
}
