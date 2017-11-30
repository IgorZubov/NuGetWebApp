package com.igor.z.validators;

import com.igor.z.modelAttributes.PackageItem;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class FileValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return PackageItem.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PackageItem packageItem = (PackageItem) o;
        if (!packageItem.getFile().getOriginalFilename().endsWith("nupkg"))
            errors.rejectValue("file", "notEqual", new Object[]{}, "File is not nuget package");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "source", "email.required");
    }
}
