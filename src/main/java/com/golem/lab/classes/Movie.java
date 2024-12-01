package com.golem.lab.classes;

import com.golem.lab.model.Client;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_seq")
    @SequenceGenerator(name = "movie_seq", sequenceName = "movie_seq")
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Column(nullable = false)
    private String name; //Поле не может быть null, Строка не может быть пустой
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "coordinates_id", nullable = false)
    private Coordinates coordinates; //Поле не может быть null
    @Column(nullable = false)
    private LocalDate creationDate = LocalDate.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int oscarsCount; //Значение поля должно быть больше 0
    @Column(nullable = false)
    private Float budget; //Значение поля должно быть больше 0, Поле не может быть null
    private float totalBoxOffice; //Значение поля должно быть больше 0
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MpaaRating mpaaRating; //Поле может быть null
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "director_id", nullable = false)
    private Person director; //Поле не может быть null
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "screenwriter_id", nullable = false)
    private Person screenwriter;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "operator_id", nullable = false)
    private Person operator; //Поле может быть null
    @Column(nullable = false)
    private Integer length; //Поле не может быть null, Значение поля должно быть больше 0
    @Column(nullable = false)
    private Long goldenPalmCount; //Значение поля должно быть больше 0, Поле может быть null
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MovieGenre genre; //Поле может быть null
//    @ManyToOne
//    private Client client;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public int getOscarsCount() {
        return oscarsCount;
    }

    public void setOscarsCount(int oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public Float getBudget() {
        return budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    public float getTotalBoxOffice() {
        return totalBoxOffice;
    }

    public void setTotalBoxOffice(float totalBoxOffice) {
        this.totalBoxOffice = totalBoxOffice;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    public Person getScreenwriter() {
        return screenwriter;
    }

    public void setScreenwriter(Person screenwriter) {
        this.screenwriter = screenwriter;
    }

    public Person getOperator() {
        return operator;
    }

    public void setOperator(Person operator) {
        this.operator = operator;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Long getGoldenPalmCount() {
        return goldenPalmCount;
    }

    public void setGoldenPalmCount(Long goldenPalmCount) {
        this.goldenPalmCount = goldenPalmCount;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

//    public Client getClient() {
//        return client;
//    }
//
//    public void setClient(Client client) {
//        this.client = client;
//    }
}
