package com.golem.lab.classes.repo;

import com.golem.lab.classes.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatesRepo extends JpaRepository<Coordinates, Integer> {
}
