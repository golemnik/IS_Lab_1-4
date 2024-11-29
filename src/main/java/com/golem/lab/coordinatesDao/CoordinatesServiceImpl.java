package com.golem.lab.coordinatesDao;

import com.golem.lab.classes.Coordinates;
import com.golem.lab.repository.CoordinatesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CoordinatesServiceImpl implements CoordinatesService {

    @Autowired
    private CoordinatesRepo coordinatesRepo;


    @Override
    public Coordinates findCoordinatesById(Long id) {
        return coordinatesRepo.findById(id);
    }

    @Override
    public List<Coordinates> findAllCoordinates() {
        return coordinatesRepo.findAll();
    }

    @Override
    public Coordinates addCoordinates(Coordinates coordinates) {
        return coordinatesRepo.save(coordinates);
    }

    @Override
    public void deleteCoordinates(int id) {
        coordinatesRepo.deleteById(id);
    }
}
