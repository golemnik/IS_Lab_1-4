package com.golem.lab.service;

import com.golem.lab.classes.Movie;
import com.golem.lab.repository.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class MovieServiceImpl implements MovieService{
    @Autowired
    private MovieRepo movieRepo;

    @Override
    public List<Movie> findAllMovies() {
        return movieRepo.findAll();
    }

    @Override
    public Movie findMovieById(int id) {
        return movieRepo.findById(id).isPresent() ? movieRepo.findById(id).get() : null;
    }

    @Override
    public void deleteMovieById(int id) {
        movieRepo.deleteById(id);
    }
}
