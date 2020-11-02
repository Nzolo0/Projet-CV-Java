package io.takima.demo.dao;

import io.takima.demo.Presentation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *DAO for Presentation Class
 */
@Repository
public interface PresentationDAO extends CrudRepository<Presentation, Long> {
}
