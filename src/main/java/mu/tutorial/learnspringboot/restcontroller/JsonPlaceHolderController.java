package mu.tutorial.learnspringboot.restcontroller;

import mu.tutorial.learnspringboot.api.JsonplaceholderApi;
import mu.tutorial.learnspringboot.model.CommentDto;
import mu.tutorial.learnspringboot.model.PostDto;
import mu.tutorial.learnspringboot.model.TodoDto;
import mu.tutorial.learnspringboot.model.UserDto;
import mu.tutorial.learnspringboot.service.JsonPlaceHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class JsonPlaceHolderController implements JsonplaceholderApi {

    private final JsonPlaceHolderService jsonPlaceHolderService;

    @Autowired
    public JsonPlaceHolderController(JsonPlaceHolderService jsonPlaceHolderService) {
        this.jsonPlaceHolderService = jsonPlaceHolderService;
    }

    @Override
    public ResponseEntity<CommentDto> retrieveComments() {
        jsonPlaceHolderService.saveAllComments();
        return JsonplaceholderApi.super.retrieveComments();
    }

    @Override
    public ResponseEntity<PostDto> retrievePosts() {
        jsonPlaceHolderService.saveAllPosts();
        return JsonplaceholderApi.super.retrievePosts();
    }

    @Override
    public ResponseEntity<TodoDto> retrieveTodos() {
        jsonPlaceHolderService.saveAllTodos();
        return JsonplaceholderApi.super.retrieveTodos();
    }

    @Override
    public ResponseEntity<UserDto> retrieveUsers() {
        jsonPlaceHolderService.saveAllUsers();
        return JsonplaceholderApi.super.retrieveUsers();
    }
}
