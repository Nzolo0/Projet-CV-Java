package io.takima.demo.dao;

import io.takima.demo.classes.file.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *DAO for File Class
 */
@Repository
public interface FileDBDAO extends JpaRepository<FileDB, String> {
}

