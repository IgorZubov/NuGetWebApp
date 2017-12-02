package com.igor.z.validators;

import com.igor.z.modelAttributes.RegistrationItem;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationFormValidator implements Validator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
            + "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" + "(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;


    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationItem.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.required");
        RegistrationItem regItem = (RegistrationItem) o;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(regItem.getEmail());
        if (!matcher.matches()) {
            errors.rejectValue("email", "invalidEmail", new Object[]{"'email'"}, "Invalid email format");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "username.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmation", "confirm.required");
        if (!regItem.getPassword().equals(regItem.getConfirmation())) {
            errors.rejectValue("confirmation", "notEqual", new Object[]{}, "Password and confirm are not equal");
        }
    }
}
