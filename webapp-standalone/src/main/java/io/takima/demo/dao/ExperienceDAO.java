package io.takima.demo.dao;

import io.takima.demo.Experience;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface ExperienceDAO extends CrudRepository<Experience, Long> {
}
