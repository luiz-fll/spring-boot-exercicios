package org.exercises.authentication;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final TokenValidationService tokenValidationService;

    public AuthenticationController(TokenValidationService tokenValidationService) {
        this.tokenValidationService = tokenValidationService;
    }

    @GetMapping("/protected")
    public ResponseEntity<Void> protectedEndpoint(RequestEntity<Void> requestEntity) {
        return tokenValidationService.validateToken(requestEntity);
    }

}
