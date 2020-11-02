package io.takima.demo.dao;

import io.takima.demo.Education;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *DAO for Education Class
 */
@Repository
public interface EducationDAO extends CrudRepository<Education, Long> {
}
