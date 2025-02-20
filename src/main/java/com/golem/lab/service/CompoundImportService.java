package com.golem.lab.service;

import org.springframework.web.multipart.MultipartFile;

public interface CompoundImportService {
    void importFile(String name, MultipartFile file) throws ServiceException;
    String exportFile(int fioId) throws ServiceException;
}
