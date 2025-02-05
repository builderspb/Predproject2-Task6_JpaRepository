package web.util.userValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckEmailValidator implements ConstraintValidator <CheckEmail,String>{

    private String endOfEmail;

    @Override
    public void initialize(CheckEmail constraintAnnotation) {
        this.endOfEmail = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            return true;
        }
        return email.endsWith(endOfEmail);
    }
}
