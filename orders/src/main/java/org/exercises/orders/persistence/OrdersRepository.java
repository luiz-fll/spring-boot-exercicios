package org.exercises.orders.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<OrderEntity, Integer> {
    List<OrderEntity> findByUserUsername(String username);
}
