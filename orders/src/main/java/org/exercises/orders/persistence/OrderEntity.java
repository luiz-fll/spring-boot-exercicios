package org.exercises.orders.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product")
    private String product;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "username")
    private UserEntity user;

    protected OrderEntity() {}

    public OrderEntity(String product, BigDecimal price,  UserEntity user) {
        this.product = product;
        this.price = price;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UserEntity getUser() {
        return user;
    }

}
