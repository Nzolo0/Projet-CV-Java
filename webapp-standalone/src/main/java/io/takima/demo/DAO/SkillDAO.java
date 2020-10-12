package io.takima.demo.DAO;

import io.takima.demo.Skill;
import io.takima.demo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface SkillDAO extends CrudRepository<Skill, Long> {

}
