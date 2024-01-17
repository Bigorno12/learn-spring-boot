package mu.tutorial.learnspringboot.repository.custom;

import mu.tutorial.learnspringboot.model.UserDto;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserCustomRepository {

    List<UserDto> findAllUsersByEqualIgnoreCase(@NonNull String name);
}
