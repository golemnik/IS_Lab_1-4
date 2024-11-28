package com.golem.lab.repository;

import com.golem.lab.coordinatesDao.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatesRepo extends JpaRepository<Coordinates, Integer> {
    Coordinates findById(Long id);
}
