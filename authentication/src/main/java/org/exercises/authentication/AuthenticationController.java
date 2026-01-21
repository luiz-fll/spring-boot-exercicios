package org.exercises.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    public AuthenticationController() {}

    @GetMapping("/requires-token")
    public ResponseEntity<Void> requiresToken() {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/always-blocked")
    public ResponseEntity<String> alwaysBlocked() {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/always-allowed")
    public ResponseEntity<String> alwaysAllowed() {
        return ResponseEntity.noContent().build();
    }
}
