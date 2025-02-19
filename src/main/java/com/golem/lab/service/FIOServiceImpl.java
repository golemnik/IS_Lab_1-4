package com.golem.lab.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.golem.lab.classes.FileImportOperation;
import com.golem.lab.classes.Movie;
import com.golem.lab.repository.FioRepo;
import com.golem.lab.repository.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FIOServiceImpl implements FIOService {

    @Autowired
    private FioRepo fioRepo;
    @Autowired
    private MovieRepo movieRepo;

    @Override
    public List<FileImportOperation> findAllFIOs() {
        return fioRepo.findAll();
    }

    @Override
    public FileImportOperation findFIOById(int id) {
        return fioRepo.findById(id).isPresent() ? fioRepo.findById(id).get() : null;
    }

    @Override
    public void deleteFIOById(int id) {
        fioRepo.deleteById(id);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void importFile(FileImportOperation fio, byte[] bytes) throws ServiceException {
        try {
            String content = new String(bytes, StandardCharsets.UTF_8);

            ObjectMapper mapper = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();
            Collection<Movie> movies = mapper.readValue(content, new TypeReference<List<Movie>>(){});

            for (Movie movie : movies) {
                movie.setId(0);
                movie.getCoordinates().setId(0);
                movie.getDirector().setId(0);
                movie.getDirector().getLocation().setId(0);
                movie.getOperator().setId(0);
                movie.getOperator().getLocation().setId(0);
                movie.getScreenwriter().setId(0);
                movie.getScreenwriter().getLocation().setId(0);
            }
            movieRepo.saveAll(movies);

            fio.setFinished(true);
            fio.setObjectsImported(movies.size());
            fioRepo.save(fio);
            fioRepo.flush();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServiceException(e);
        }
    }
}
