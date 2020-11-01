package io.takima.demo.dao;

import io.takima.demo.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface SkillDAO extends CrudRepository<Skill, Long> {

}
