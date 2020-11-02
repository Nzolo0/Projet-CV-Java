package io.takima.demo.dao;

import io.takima.demo.Hobby;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *DAO for Hobby Class
 */
@Repository
public interface HobbyDAO extends CrudRepository<Hobby, Long> {
}
