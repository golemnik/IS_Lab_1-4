package com.golem.lab.repository;

import com.golem.lab.classes.FileImportOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FioRepo extends JpaRepository<FileImportOperation, Integer> {
}
