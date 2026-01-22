package org.exercises.cryptography;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDTO> findAll() {
        return userService.findAllUsers();
    }

    @GetMapping("/users/{id}")
    public UserDTO findOne(@PathVariable Long id) {
        return userService.findUser(id);
    }

    @PostMapping("/users")
    public UserDTO save(@RequestBody UserDTO user) {
        return userService.addUser(user);
    }

    @PutMapping("/users/{id}")
    public UserDTO update(@PathVariable Long id, @RequestBody UserDTO user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    public UserDTO delete(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

}
