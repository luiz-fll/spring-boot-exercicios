package org.exercises.pointsofinterest;

import jakarta.persistence.*;

@Entity
@Table(name = "poi")
public class POIEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer x;
    private Integer y;

    public Long getId() {
        return id;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public String getName() {
        return name;
    }

}
