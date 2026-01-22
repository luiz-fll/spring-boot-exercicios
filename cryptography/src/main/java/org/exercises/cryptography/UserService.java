package org.exercises.cryptography;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO findUser(Long id) throws NoSuchUserExistsException {
        return UserDTO.from(userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchUserExistsException("User not found")));
    }

    public List<UserDTO> findAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(UserDTO.from(user)) );

        return users;
    }

    public UserDTO updateUser(Long id, UserDTO updatedUser) throws NoSuchUserExistsException {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NoSuchUserExistsException("User not found"));

        if (updatedUser.userDocument() != null) {
            user.setUserDocument(updatedUser.userDocument());
        }

        if (updatedUser.creditCardToken() != null) {
            user.setCreditCardToken(updatedUser.creditCardToken());
        }

        if (updatedUser.value() != null) {
            user.setValue(updatedUser.value());
        }

        return UserDTO.from(userRepository.save(user));
    }

    public UserDTO addUser(UserDTO user) throws MissingDataException {
        if (user.userDocument() == null || user.creditCardToken() == null || user.value() == null) {
            throw new MissingDataException("Missing data");
        }

        UserEntity newUser = new UserEntity(user.userDocument(), user.creditCardToken(), user.value());

        return UserDTO.from(userRepository.save(newUser));
    }

    public UserDTO deleteUser(Long id) throws NoSuchUserExistsException {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NoSuchUserExistsException("User not found"));
        userRepository.deleteById(id);

        return UserDTO.from(user);
    }

}
