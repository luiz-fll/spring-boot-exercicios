package org.exercises.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TokenValidationService {

    public TokenValidationService() {}

    public ResponseEntity<Void> validateToken(RequestEntity<Void> responseEntity) {
        String requestToken = responseEntity.getHeaders().getFirst("Authorization");
        String token = "Bearer rightToken";
        if (requestToken != null && requestToken.equals(token)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
