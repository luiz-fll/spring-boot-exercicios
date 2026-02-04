package org.exercises.orders;

import org.exercises.orders.dto.ErrorDTO;
import org.exercises.orders.dto.LoginDTO;
import org.exercises.orders.dto.OrderDTO;
import org.exercises.orders.dto.TokenDTO;
import org.exercises.orders.security.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrdersController {

    private final LoginService loginService;
    private final OrdersService ordersService;

    public OrdersController(LoginService loginService, OrdersService ordersService) {
        this.loginService = loginService;
        this.ordersService = ordersService;
    }

    @GetMapping("/orders")
    public List<OrderDTO> orders(@AuthenticationPrincipal String username) {
        return ordersService.getOrders(username)
                .stream()
                .map(OrderDTO::from)
                .toList();
    }

    @PostMapping("/auth/login")
    public TokenDTO login(@RequestBody LoginDTO data) {
        return new TokenDTO(loginService.attemptLogin(data.username(), data.password()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleBadCredentialsException(BadCredentialsException e) {
        return new  ErrorDTO(e.getMessage());
    }

}
