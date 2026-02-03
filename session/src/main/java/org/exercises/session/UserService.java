package org.exercises.session;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    public UserDTO getUser(String email) throws UserNotFoundException {
        return UserDTO
                .from(repository
                        .findByEmail(email)
                        .orElseThrow(UserNotFoundException::new)
                );
    }

    public MessageDTO sendMessage(String message) {
        return new MessageDTO(message);
    }

}
