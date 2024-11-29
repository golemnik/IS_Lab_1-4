package com.golem.lab.repository;

import com.golem.lab.classes.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Integer> {
//    @Override
//    Movie save(Movie movie);
}
