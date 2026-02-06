package org.exercises.resourceserver;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceServerController {

    @GetMapping("/public")
    public PublicDto publicEndpoint() {
        return new PublicDto("public endpoint");
    }

    @GetMapping("/profile")
    public ProfileDto profileEndpoint(@AuthenticationPrincipal Jwt jwt) {
        return new ProfileDto(jwt.getSubject(), jwt.getClaim("preferred_username"), jwt.getClaim("email"), jwt.getIssuer().toString());
    }

}
