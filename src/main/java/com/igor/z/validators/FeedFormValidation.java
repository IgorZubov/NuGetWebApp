package com.igor.z.validators;

import com.igor.z.modelAttributes.FeedItem;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class FeedFormValidation implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return FeedItem.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        FeedItem feedItem = (FeedItem) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "feedName", "name.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "feedSource", "source.required");
    }
}
