package org.exercises.securepassword;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class SecurePasswordService {

    //- Verificar se a senha possui pelo menos 08 caracteres.
    //- Verificar se a senha contém pelo menos uma letra maiúscula.
    //- Verificar se a senha contém pelo menos uma letra minúscula.
    //- Verificar se a senha contém pelo menos um dígito numérico.
    //- Verificar se a senha contém pelo menos um character especial (e.g, !@#$%).
    private static final Pattern UPPER = Pattern.compile("[A-Z]");
    private static final Pattern LOWER = Pattern.compile("[a-z]");
    private static final Pattern DIGIT = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL = Pattern.compile("[^A-Za-z0-9]");

    public SecurePasswordService() {}

    public void validate(String password) throws InvalidPasswordException {
        Map<String, Boolean> validationSteps = new LinkedHashMap<>();

        validationSteps.put("at-least-8-characters", password.length() >= 8);
        validationSteps.put("at-least-1-upper-case-character", UPPER.matcher(password).find());
        validationSteps.put("at-least-1-lower-case-character", LOWER.matcher(password).find());
        validationSteps.put("at-least-1-numeric-character", DIGIT.matcher(password).find());
        validationSteps.put("at-least-1-special-character", SPECIAL.matcher(password).find());

        if (validationSteps.containsValue(Boolean.FALSE)) {
            throw new InvalidPasswordException(validationSteps);
        }
    }

}
