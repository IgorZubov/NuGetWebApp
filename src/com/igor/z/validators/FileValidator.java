package com.igor.z.validators;

import com.igor.z.modelAttributes.PackageItem;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return PackageItem.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PackageItem packageItem = (PackageItem) o;

        if (!MultipartFile.class.isAssignableFrom(packageItem.getFile().getClass()))
            errors.rejectValue("file", "filerequired", new Object[]{}, "Please select nuget package file.");
        else if (!((MultipartFile)packageItem.getFile()).getOriginalFilename().endsWith("nupkg"))
            errors.rejectValue("file", "filerequired", new Object[]{}, "Please select nuget package file.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "source", "source.required");
    }
}
