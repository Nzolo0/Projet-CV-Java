package io.takima.demo.DAO;

import io.takima.demo.Education;
import io.takima.demo.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface EducationDAO extends CrudRepository<Education, Long> {
}
