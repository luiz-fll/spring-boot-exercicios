package org.exercises.session;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService userService) {
        this.service = userService;
    }

    @GetMapping("/me")
    public UserDTO userEndpoint(Principal principal) {
        return service.getUser(principal.getName());
    }

    @GetMapping("/public")
    public MessageDTO publicEndpoint() {
        return service.sendMessage("public endpoint");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageDTO handleUserNotFoundException() {
        return new MessageDTO("User not found");
    }

}
