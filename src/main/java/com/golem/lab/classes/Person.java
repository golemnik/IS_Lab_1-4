package com.golem.lab.classes;

import jakarta.persistence.*;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @SequenceGenerator(name = "person_seq", sequenceName = "person_seq")
    private long id;
    @Column(nullable = false)
    private String name; //Поле не может быть null, Строка не может быть пустой
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Color eyeColor; //Поле не может быть null
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Color hairColor; //Поле не может быть null
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location; //Поле может быть null
    @Column(nullable = false)
    private Float height; //Поле не может быть null, Значение поля должно быть больше 0
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Country nationality; //Поле может быть null

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }
}
