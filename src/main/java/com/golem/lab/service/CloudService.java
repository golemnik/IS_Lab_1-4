package com.golem.lab.service;

public interface CloudService {
    void uploadFile(int fioId, byte[] bytes) throws ServiceException;
    String downloadFile(int fioId) throws ServiceException;
    void removeFile(int fio) throws ServiceException;
    void commitFile(int fio, String fileName, String description) throws ServiceException;
}
