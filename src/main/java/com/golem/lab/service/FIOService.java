package com.golem.lab.service;

import com.golem.lab.classes.FileImportOperation;

import java.util.List;

public interface FIOService {
    List<FileImportOperation> findAllFIOs();
    FileImportOperation findFIOById(int id);
    void deleteFIOById(int id);

    void importFile(FileImportOperation fio, byte[] bytes) throws ServiceException;
}
