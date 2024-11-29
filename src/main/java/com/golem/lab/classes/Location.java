package com.golem.lab.classes;

import jakarta.persistence.*;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_seq")
    @SequenceGenerator(name = "location_seq", sequenceName = "location_seq")
    private long id;
    @Column(nullable = false)
    private float x;
    @Column(nullable = false)
    private long y;
    @Column(nullable = false)
    private Integer z; //Поле не может быть null
    @Column(nullable = false, length = 658)
    private String name; //Длина строки не должна быть больше 658, Поле не может быть null

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
