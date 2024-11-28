package com.golem.lab.coordinatesDao;

import jakarta.persistence.*;

@Entity
@Table(name = "coordinates")
public class Coordinates {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Coordinates_seq")
    @SequenceGenerator(name = "Coordinates_seq", sequenceName = "Coordinates_seq")
    private Long id;
    @Column(name = "x")
    private Integer x; //Поле не может быть null
    @Column(name = "Y")
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

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}