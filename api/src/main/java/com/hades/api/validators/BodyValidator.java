package com.hades.api.validators;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.hades.api.utils.constants;
import com.hades.api.utils.Exceptions.InvalidParameterException;

@Component
public class BodyValidator {

    private long maxFileSize = (long) constants.FILE_MAX_SIZE;

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
            throw new InvalidParameterException(
                    violationList.toString().substring(1, violationList.toString().length() - 1));
        }
    }

    public void multipartValidate(MultipartFile[] files) {
        for (MultipartFile file : files) {
            if (!isFileTypeValid(file) || !isFileSizeValid(file) || !isFileContentNotMalicious(file)) {
                throw new InvalidParameterException("Invalid file content, type or size");
            }

        }
    }

    private Boolean isFileTypeValid(MultipartFile file) {
        String fileType = file.getContentType();
        if (fileType == null || !fileType.equals(constants.FILE_CONTENT_TYPE))
            return false;

        return true;
    }

    private Boolean isFileSizeValid(MultipartFile file) {
        long fileSize = file.getSize();
        if (fileSize == 0 && fileSize > maxFileSize)
            return false;

        return true;
    }

    private Boolean isFileContentNotMalicious(MultipartFile file) {
        String fileContent = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                fileContent += line;
            }
        } catch (Exception e) {
            throw new InvalidParameterException("could not read file");
        }

        if (fileContent.contains("<script>"))
            return false;

        return true;
    }
}
