package io.takima.demo.DAO;

import io.takima.demo.Experience;
import io.takima.demo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface ExperienceDAO extends CrudRepository<Experience, Long> {

}
