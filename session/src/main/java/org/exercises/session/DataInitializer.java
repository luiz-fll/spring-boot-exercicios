package org.exercises.session;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder encoder) {
        this.repository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        UserEntity user = new UserEntity("teste@teste.com", encoder.encode("abc123"));
        repository.save(user);
    }

}
