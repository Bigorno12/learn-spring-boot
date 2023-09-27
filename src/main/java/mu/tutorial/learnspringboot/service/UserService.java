package mu.tutorial.learnspringboot.service;

import mu.tutorial.learnspringboot.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Page<UserDto> retrieveUsers(Pageable pageable);

    UserDto updateUserEntity(UserDto userDto);

    List<UserDto> retrieveUsersByName(String name);
}
