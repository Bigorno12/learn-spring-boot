package mu.tutorial.learnspringboot.repository;

import mu.tutorial.learnspringboot.entity.Post;
import mu.tutorial.learnspringboot.repository.custom.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends GenericRepository<Post> {
}
