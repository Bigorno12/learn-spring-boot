package mu.tutorial.learnspringboot.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

@Slf4j
@Service
@Transactional
public class JsonPlaceHolderServiceImpl implements JsonPlaceHolderService {

    private final RestClient restClient;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final TodosRepository todosRepository;
    private final CommentRepository commentRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public JsonPlaceHolderServiceImpl(@Qualifier("restClient") RestClient restClient,
                                      UserRepository userRepository,
                                      PostRepository postRepository,
                                      TodosRepository todosRepository,
                                      CommentRepository commentRepository, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.todosRepository = todosRepository;
        this.commentRepository = commentRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void saveAllUsers() {

        List<User> users = restClient.get()
                .uri("/users")
                .exchange((request, response) -> switch (response.getStatusCode()) {
                        case HttpStatus.OK -> objectMapper.readValue(response.getBody(), new TypeReference<>() {});
                        case HttpStatus.NO_CONTENT, HttpStatus.NOT_FOUND -> throw new JsonPlaceHolderException("Failed to get users from jsonplaceholder.typicode.com");
                        default -> throw new IllegalStateException("Unexpected value: " + response.getStatusCode());
                    }
                );

        userRepository.saveAll(users);
    }

    @Override
    public void saveAllTodos() {
        ResponseEntity<TodoJsonPlaceHolder[]> todoJsonPlaceHolderEntity = restClient.get()
                .uri("/todos")
                .retrieve()
                .onStatus(HttpStatusCode::is2xxSuccessful, (request, response) -> response.getBody())
                .toEntity(TodoJsonPlaceHolder[].class);

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

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<PostJsonPlaceHolder>> futurePosts = IntStream.rangeClosed(1, 100)
                    .mapToObj(index -> executor.submit(() -> restClient.get()
                            .uri("/posts/" + index)
                            .retrieve()
                            .body(PostJsonPlaceHolder.class)))
                    .toList();

            List<Post> posts = futurePosts.stream()
                    .map(futurePost -> {
                        try {
                            return futurePost.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new JsonPlaceHolderException("Failed to get posts from jsonplaceholder.typicode.com");
                        }
                    })
                    .map(this::mapPostJsonPlaceHolderToEntity)
                    .toList();

            postRepository.saveAll(posts);

        }
    }

    @Override
    public void saveAllComments() {

        List<CompletableFuture<Comment>> futureComments = IntStream.rangeClosed(1, 500)
                .mapToObj(index -> CompletableFuture.supplyAsync(() -> restClient.get()
                        .uri("/comments/" + index)
                        .retrieve()
                        .body(CommentJsonPlaceHolder.class)).thenApply(this::mapCommentJsonPlaceHolderToEntity))
                .toList();

        List<Comment> comments = futureComments.stream()
                .map(CompletableFuture::join)
                .filter(comment -> comment.getName() != null).toList();

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
