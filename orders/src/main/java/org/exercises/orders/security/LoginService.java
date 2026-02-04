package org.exercises.orders.security;

import org.exercises.orders.persistence.UserEntity;
import org.exercises.orders.persistence.UsersRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UsersRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public LoginService(UsersRepository repository, PasswordEncoder encoder,  JwtService jwtService) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public String attemptLogin(String username, String password) throws BadCredentialsException {
        UserEntity user = repository.findById(username).orElseThrow(() -> new BadCredentialsException("Invalid username."));

        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password.");
        }

        return jwtService.generateToken(username);
    }

}
