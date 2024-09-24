package com.example.api.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validator;

import com.example.api.utils.Exceptions.InvalidParameterException;

@Component
public class BodyValidator {

    /* validate annotation must in root attribute of the class */
    public void validate(Object object) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        List<String> violationList = new ArrayList<String>();
        for (ConstraintViolation<Object> violation : violations) {
            System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
            violationList.add(violation.getMessage());

        }

        if (violationList.size() > 0) {
            throw new InvalidParameterException(violationList.toString());
        }
    }
}
