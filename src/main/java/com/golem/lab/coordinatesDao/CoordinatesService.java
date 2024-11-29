package com.golem.lab.coordinatesDao;

import com.golem.lab.classes.Coordinates;

import java.util.List;

public interface CoordinatesService {
    public Coordinates findCoordinatesById(Long id);
    public List<Coordinates> findAllCoordinates();
    public Coordinates addCoordinates(Coordinates coordinates);
    public void deleteCoordinates(int id);

}
