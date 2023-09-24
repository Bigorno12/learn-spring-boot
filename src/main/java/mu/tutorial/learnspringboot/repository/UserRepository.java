package mu.tutorial.learnspringboot.repository;

import mu.tutorial.learnspringboot.entity.User;
import mu.tutorial.learnspringboot.repository.custom.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository<User> {
}
