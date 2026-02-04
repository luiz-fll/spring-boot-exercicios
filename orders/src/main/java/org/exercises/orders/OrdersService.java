package org.exercises.orders;

import org.exercises.orders.persistence.OrderEntity;
import org.exercises.orders.persistence.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {

    private final OrdersRepository repository;

    public OrdersService(OrdersRepository ordersRepository) {
        this.repository = ordersRepository;
    }

    public List<OrderEntity> getOrders(String username) {
        return repository.findByUserUsername(username);
    }

}
