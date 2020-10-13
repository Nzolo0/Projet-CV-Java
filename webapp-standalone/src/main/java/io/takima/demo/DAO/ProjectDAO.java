package io.takima.demo.DAO;

import io.takima.demo.Education;
import io.takima.demo.Projects;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface ProjectDAO extends CrudRepository<Projects, Long> {
}
