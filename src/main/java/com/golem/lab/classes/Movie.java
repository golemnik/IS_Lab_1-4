package com.golem.lab.classes;

import com.golem.lab.coordinatesDao.Coordinates;

public class Movie {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int oscarsCount; //Значение поля должно быть больше 0
    private Float budget; //Значение поля должно быть больше 0, Поле не может быть null
    private float totalBoxOffice; //Значение поля должно быть больше 0
    private MpaaRating mpaaRating; //Поле может быть null
    private Person director; //Поле не может быть null
    private Person screenwriter;
    private Person operator; //Поле может быть null
    private long length; //Значение поля должно быть больше 0
    private long goldenPalmCount; //Значение поля должно быть больше 0
    private MovieGenre genre; //Поле может быть null
}
