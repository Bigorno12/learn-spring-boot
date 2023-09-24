package mu.tutorial.learnspringboot.repository;

import mu.tutorial.learnspringboot.entity.Comment;
import mu.tutorial.learnspringboot.repository.custom.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends GenericRepository<Comment> {
}
