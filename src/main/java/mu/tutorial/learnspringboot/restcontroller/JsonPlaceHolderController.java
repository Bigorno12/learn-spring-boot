package mu.tutorial.learnspringboot.restcontroller;

import mu.tutorial.learnspringboot.service.JsonPlaceHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/jsonplaceholder")
public class JsonPlaceHolderController {

    private final JsonPlaceHolderService jsonPlaceHolderService;

    @Autowired
    public JsonPlaceHolderController(JsonPlaceHolderService jsonPlaceHolderService) {
        this.jsonPlaceHolderService = jsonPlaceHolderService;
    }

    @GetMapping("/comments")
    public void saveAllComments() {
        jsonPlaceHolderService.saveAllComments();
    }

    @GetMapping("/posts")
    public void saveAllPosts() {
        jsonPlaceHolderService.saveAllPosts();
    }

    @GetMapping("/todos")
    public void saveAllTodos() {
        jsonPlaceHolderService.saveAllTodos();
    }

    @GetMapping("/users")
    public void saveAllUsers() {
        jsonPlaceHolderService.saveAllUsers();
    }
}
