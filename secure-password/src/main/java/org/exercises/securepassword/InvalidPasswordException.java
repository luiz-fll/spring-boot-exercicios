package org.exercises.securepassword;

import java.util.Map;

public class InvalidPasswordException extends RuntimeException {

    private final Map<String, Boolean> validationSteps;

    public InvalidPasswordException(Map<String, Boolean> validationSteps) {
        this.validationSteps = validationSteps;
    }

    public Map<String, Boolean> getValidationSteps() {
        return validationSteps;
    }

}
