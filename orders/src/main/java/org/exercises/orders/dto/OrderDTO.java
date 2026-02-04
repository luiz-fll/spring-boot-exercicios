package org.exercises.orders.dto;

import org.exercises.orders.persistence.OrderEntity;

import java.math.BigDecimal;

public record OrderDTO(Long id, String product, BigDecimal price) {

    public static OrderDTO from(OrderEntity order) {
        return new OrderDTO(order.getId(), order.getProduct(), order.getPrice());
    }

}
