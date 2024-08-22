package com.example.api.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.api.utils.Exceptions.DataFormateException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

public class BodyValidator {

    public void validate(Object object) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        jakarta.validation.Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        List<String> violationList = new ArrayList<String>();

        // Will run for loop to see if there are any validation error
        for (ConstraintViolation<Object> violation : violations) {
            System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
            violationList.add(violation.getMessage());

        }

        if (violationList.size() > 0) {
            throw new DataFormateException(violationList.toString());
        }

    }
}
