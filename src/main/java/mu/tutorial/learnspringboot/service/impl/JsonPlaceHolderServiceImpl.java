package mu.tutorial.learnspringboot.service.impl;

import lombok.extern.slf4j.Slf4j;
import mu.tutorial.learnspringboot.config.RestTemplateConfiguration;
import mu.tutorial.learnspringboot.entity.Comment;
import mu.tutorial.learnspringboot.entity.Post;
import mu.tutorial.learnspringboot.entity.Todo;
import mu.tutorial.learnspringboot.entity.User;
import mu.tutorial.learnspringboot.exception.JsonPlaceHolderException;
import mu.tutorial.learnspringboot.jsonplaceholder.CommentJsonPlaceHolder;
import mu.tutorial.learnspringboot.jsonplaceholder.PostJsonPlaceHolder;
import mu.tutorial.learnspringboot.jsonplaceholder.TodoJsonPlaceHolder;
import mu.tutorial.learnspringboot.repository.CommentRepository;
import mu.tutorial.learnspringboot.repository.PostRepository;
import mu.tutorial.learnspringboot.repository.TodosRepository;
import mu.tutorial.learnspringboot.repository.UserRepository;
import mu.tutorial.learnspringboot.service.JsonPlaceHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class JsonPlaceHolderServiceImpl implements JsonPlaceHolderService {

    private final RestTemplateConfiguration restTemplateConfiguration;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final TodosRepository todosRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public JsonPlaceHolderServiceImpl(RestTemplateConfiguration restTemplateConfiguration, UserRepository userRepository, PostRepository postRepository, TodosRepository todosRepository, CommentRepository commentRepository) {
        this.restTemplateConfiguration = restTemplateConfiguration;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.todosRepository = todosRepository;
        this.commentRepository = commentRepository;
    }
    @Override
    public void saveAllUsers() {
        ResponseEntity<User[]> userEntity = restTemplateConfiguration.restTemplate()
                .getForEntity("/users", User[].class);

        List<User> users = Optional.ofNullable(userEntity.getBody())
                .map(Arrays::asList)
                .orElseThrow(() -> new JsonPlaceHolderException("Failed to get users from jsonplaceholder.typicode.com"));

        userRepository.saveAll(users);
    }

    @Override
    public void saveAllTodos() {
        ResponseEntity<TodoJsonPlaceHolder[]> todoJsonPlaceHolderEntity = restTemplateConfiguration.restTemplate()
                .getForEntity("/todos", TodoJsonPlaceHolder[].class);

        List<TodoJsonPlaceHolder> todoJsonPlaceHolders = Optional.ofNullable(todoJsonPlaceHolderEntity.getBody())
                .map(Arrays::asList)
                .orElseThrow(() -> new JsonPlaceHolderException("Failed to get todos from jsonplaceholder.typicode.com"));

        List<Todo> todos = todoJsonPlaceHolders.stream()
                .map(this::mapTodosJsonPlaceHolderToEntity)
                .toList();

        todosRepository.saveAll(todos);
    }

    @Override
    public void saveAllPosts() {
        ResponseEntity<PostJsonPlaceHolder[]> postJsonPlaceHolderEntity = restTemplateConfiguration.restTemplate()
                .getForEntity("/posts", PostJsonPlaceHolder[].class);

        List<PostJsonPlaceHolder> postJsonPlaceHolders = Optional.ofNullable(postJsonPlaceHolderEntity.getBody())
                .map(Arrays::asList)
                .orElseThrow(() -> new JsonPlaceHolderException("Failed to get posts from jsonplaceholder.typicode.com"));

        List<Post> posts = postJsonPlaceHolders.stream()
                .map(this::mapPostJsonPlaceHolderToEntity)
                .toList();

        postRepository.saveAll(posts);
    }

    @Override
    public void saveAllComments() {
        ResponseEntity<CommentJsonPlaceHolder[]> commentJsonPlaceHolderEntity = restTemplateConfiguration.restTemplate()
                .getForEntity("/comments", CommentJsonPlaceHolder[].class);

        List<CommentJsonPlaceHolder> commentJsonPlaceHolders = Optional.ofNullable(commentJsonPlaceHolderEntity.getBody())
                .map(Arrays::asList)
                .orElseThrow(() -> new JsonPlaceHolderException("Failed to get comments from jsonplaceholder.typicode.com"));

        List<Comment> comments = commentJsonPlaceHolders.stream()
                .map(this::mapCommentJsonPlaceHolderToEntity)
                .toList();

        commentRepository.saveAll(comments);

    }

    private Todo mapTodosJsonPlaceHolderToEntity(TodoJsonPlaceHolder todoJsonPlaceHolder) {
        return Todo.builder()
                .id(todoJsonPlaceHolder.id())
                .user(userRepository.findById(todoJsonPlaceHolder.userId()).orElseThrow(() -> new JsonPlaceHolderException("User not found")))
                .title(todoJsonPlaceHolder.title())
                .completed(todoJsonPlaceHolder.completed())
                .build();
    }

    private Post mapPostJsonPlaceHolderToEntity(PostJsonPlaceHolder postJsonPlaceHolder) {
        return Post.builder()
                .id(postJsonPlaceHolder.id())
                .user(userRepository.findById(postJsonPlaceHolder.userId()).orElseThrow(() -> new JsonPlaceHolderException("User not found")))
                .title(postJsonPlaceHolder.title())
                .body(postJsonPlaceHolder.body())
                .build();
    }

    private Comment mapCommentJsonPlaceHolderToEntity(CommentJsonPlaceHolder commentJsonPlaceHolder) {
        return Comment.builder()
                .id(commentJsonPlaceHolder.id())
                .post(postRepository.findById(commentJsonPlaceHolder.postId()).orElseThrow(() -> new JsonPlaceHolderException("Post not found")))
                .email(commentJsonPlaceHolder.email())
                .body(commentJsonPlaceHolder.body())
                .build();
    }

}
