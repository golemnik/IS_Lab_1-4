package com.golem.lab.service;

import com.golem.lab.classes.FileImportOperation;
import com.golem.lab.repository.FioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FIOServiceImpl implements FIOService {

    @Autowired
    private FioRepo fioRepo;

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
}
