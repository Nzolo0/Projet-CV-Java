package io.takima.demo.dao;

import io.takima.demo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *DAO for User Class
 */
@Repository
public interface UserDAO extends CrudRepository<User, Long> {
}
