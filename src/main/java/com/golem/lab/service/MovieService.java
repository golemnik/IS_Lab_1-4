package com.golem.lab.service;

import com.golem.lab.classes.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> findAllMovies();
    Movie findMovieById(int id);
    void deleteMovieById(int id);
}
