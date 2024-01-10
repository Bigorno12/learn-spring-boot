package mu.tutorial.learnspringboot.restcontroller;

import mu.tutorial.learnspringboot.api.JsonplaceholderApi;
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
    public ResponseEntity<Void> retrieveComments() {
        jsonPlaceHolderService.saveAllComments();
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> retrievePosts() {
        jsonPlaceHolderService.saveAllPosts();
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> retrieveTodos() {
        jsonPlaceHolderService.saveAllTodos();
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> retrieveUsers() {
        jsonPlaceHolderService.saveAllUsers();
        return ResponseEntity.ok().build();
    }
}
