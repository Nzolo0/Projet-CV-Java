package io.takima.demo.dao;

import io.takima.demo.Presentation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface PresentationDAO extends CrudRepository<Presentation, Long> {
}
