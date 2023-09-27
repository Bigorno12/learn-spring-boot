package mu.tutorial.learnspringboot.restcontroller;

import mu.tutorial.learnspringboot.dto.UserDto;
import mu.tutorial.learnspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public Page<UserDto> retrieveAllUsers(@RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "size",defaultValue = "5") Integer size) {
        return userService.retrieveUsers(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));
    }

    @PutMapping("/update")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userService.updateUserEntity(userDto);
    }

    @GetMapping("/names")
    public List<UserDto> retrieveUsersByName(@RequestParam(name = "name") String name) {
        return userService.retrieveUsersByName(name);
    }
}
