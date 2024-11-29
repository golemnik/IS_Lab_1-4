package com.golem.lab.classes;

import jakarta.persistence.*;

@Entity
@Table(name = "coordinates")
public class Coordinates {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Coordinates_seq")
    @SequenceGenerator(name = "Coordinates_seq", sequenceName = "Coordinates_seq")
    private long id;
    @Column(nullable = false)
    private Integer x; //Поле не может быть null
    @Column(nullable = false)
    private Integer y; //Поле не может быть null

    public Integer getX() {
        return x;
    }
    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }
    public void setY(Integer y) {
        this.y = y;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
}
