package com.golem.lab.service;

import com.golem.lab.classes.FileImportOperation;
import com.golem.lab.util.TwoPhasesTransaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(rollbackFor = Exception.class)
public class CompoundImportServiceImpl implements CompoundImportService {

    @Autowired
    private CloudService cloudService;

    @Autowired
    private FIOService fioService;

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = ServiceException.class)
    public void importFile(String user, MultipartFile file) throws ServiceException {
        FileImportOperation fio = new FileImportOperation();
        TwoPhasesTransaction tr = new TwoPhasesTransaction(entityManager);
        tr.inTransaction(() -> { // Phase 1
            try {
                fio.setClient(userService.findByUsername(user));
                byte[] bytes = file.getBytes();
                fioService.importFile(fio, bytes);
                cloudService.uploadFile(fio.getId(), bytes);
                // if (true) throw new ServiceException("TEST 1");
            } catch (Exception e) {
                throw new ServiceException(e);
            }
        }, () -> { // Phase 2
            cloudService.commitFile(fio.getId(), file.getOriginalFilename(),  "imported movies");
            // if (true) throw new ServiceException("TEST 2");
        }, () -> { // rollback hook
            if (fio.getId() != 0) {
                cloudService.removeFile(fio.getId());
            }
        });
    }

    @Override
    public String exportFile(int fioId) throws ServiceException {
        return cloudService.downloadFile(fioId);
    }
}
