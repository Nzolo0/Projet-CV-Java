package io.takima.demo.dao;

import io.takima.demo.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface ProjectDAO extends CrudRepository<Project, Long> {
}
