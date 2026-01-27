package org.exercises.securepassword;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SecurePasswordController {

    private final SecurePasswordService service;

    public SecurePasswordController(SecurePasswordService service) {
        this.service = service;
    }

    @PostMapping("/validate-password")
    public ResponseEntity<Void> validatePassword(@RequestBody RequestDTO request) throws InvalidPasswordException {
        service.validate(request.password());

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Boolean> InvalidPasswordExceptionHandler(InvalidPasswordException e) {
        return e.getValidationSteps();
    }

}
