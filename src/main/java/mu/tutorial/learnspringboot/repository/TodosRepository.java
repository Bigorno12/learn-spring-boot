package mu.tutorial.learnspringboot.repository;

import mu.tutorial.learnspringboot.entity.Todo;
import mu.tutorial.learnspringboot.repository.custom.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodosRepository extends GenericRepository<Todo> {


}
