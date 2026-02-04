package org.exercises.orders.persistence;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;

    public DataInitializer(OrdersRepository ordersRepository, UsersRepository usersRepository, PasswordEncoder encoder) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        ordersRepository.deleteAll();
        usersRepository.deleteAll();

        UserEntity admin = new UserEntity("admin", encoder.encode("admin"));
        UserEntity user  = new UserEntity("user", encoder.encode("user"));
        usersRepository.save(admin);
        usersRepository.save(user);

        OrderEntity adminOrder1 = new OrderEntity("A", new BigDecimal(1200), admin);
        OrderEntity adminOrder2 = new OrderEntity("B", new BigDecimal("2000.50"), admin);
        OrderEntity adminOrder3 = new OrderEntity("C", new BigDecimal(300), admin);

        OrderEntity userOrder1 = new OrderEntity("D", new BigDecimal(4000), user);
        OrderEntity userOrder2 = new OrderEntity("E", new BigDecimal("500.25"), user);
        OrderEntity userOrder3 = new OrderEntity("F", new BigDecimal(800), user);

        ordersRepository.save(adminOrder1);
        ordersRepository.save(adminOrder2);
        ordersRepository.save(adminOrder3);
        ordersRepository.save(userOrder1);
        ordersRepository.save(userOrder2);
        ordersRepository.save(userOrder3);
    }

}
