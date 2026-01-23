package org.exercises.cryptography;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testFindUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new UserEntity("test", "test", 0L)));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        UserDTO userDTO = userService.findUser(1L);
        assertThat(userDTO).isEqualTo(new UserDTO(null, "test", "test", 0L));
        assertThrows(NoSuchUserExistsException.class, () -> userService.findUser(2L));
    }

    @Test
    public void testFindAllUsers() {
        List<UserEntity> inputUsers = java.util.Arrays.asList(
                new UserEntity("test", "test", 0L),
                new UserEntity("test2", "test2", 0L),
                new UserEntity("test3", "test3", 0L));

        List<UserDTO> expectedUserDTOs = java.util.Arrays.asList(
                new UserDTO(null, "test", "test", 0L),
                new UserDTO(null, "test2", "test2", 0L),
                new UserDTO(null, "test3", "test3", 0L));

        when(userRepository.findAll()).thenReturn(inputUsers);

        List<UserDTO> userDTOs = userService.findAllUsers();
        assertThat(userDTOs.size()).isEqualTo(3);
        assertThat(userDTOs.containsAll(expectedUserDTOs)).isTrue();
    }

    @Test
    public void testUpdateUser() {
        UserEntity inputUserEntity = new UserEntity("test", "test", 0L);
        UserEntity expectedUserEntity = new UserEntity("edited", "edited", 0L);
        UserDTO inputDTO = new UserDTO(null, "test", "test", 0L);
        UserDTO expectedDTO = new UserDTO(null, "edited", "edited", 0L);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(inputUserEntity));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(expectedUserEntity);

        assertThat(userService.updateUser(1L, inputDTO)).isEqualTo(expectedDTO);
        assertThrows(NoSuchUserExistsException.class, () -> userService.updateUser(2L, inputDTO));
    }

    @Test
    public void testAddUser() {
        UserEntity userEntity = new UserEntity("test", "test", 0L);
        UserDTO inputDTO = new UserDTO(999L, "test", "test", 0L);
        UserDTO nullUserDocument = new UserDTO(null, null, "test", 0L);
        UserDTO nullCreditCardToken = new UserDTO(null, "test", null, 0L);
        UserDTO nullValue = new UserDTO(null, "test", "test", null);
        UserDTO expectedDTO = new UserDTO(null, "test", "test", 0L);

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        assertThat(userService.addUser(inputDTO)).isEqualTo(expectedDTO);
        assertThrows(MissingDataException.class, () -> userService.addUser(nullUserDocument));
        assertThrows(MissingDataException.class, () -> userService.addUser(nullCreditCardToken));
        assertThrows(MissingDataException.class, () -> userService.addUser(nullValue));
    }

    @Test
    public void testDeleteUser() {
        UserEntity userEntity = new UserEntity("test", "test", 0L);
        UserDTO expectedDTO = new UserDTO(null, "test", "test", 0L);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThat(userService.deleteUser(1L)).isEqualTo(expectedDTO);
        assertThrows(NoSuchUserExistsException.class, () -> userService.deleteUser(2L));
    }

}
