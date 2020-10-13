package io.takima.demo.DAO;

import io.takima.demo.Hobbies;
import io.takima.demo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface HobbiesDAO extends CrudRepository<Hobbies, Long> {

}
