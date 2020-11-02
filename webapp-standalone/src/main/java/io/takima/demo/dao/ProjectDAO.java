package io.takima.demo.dao;

import io.takima.demo.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *DAO for Project Class
 */
@Repository
public interface ProjectDAO extends CrudRepository<Project, Long> {
}
