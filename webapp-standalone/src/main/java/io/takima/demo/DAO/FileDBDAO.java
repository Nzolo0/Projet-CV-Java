package io.takima.demo.DAO;

import io.takima.demo.Classes.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface FileDBDAO extends JpaRepository<FileDB, String> {
}

