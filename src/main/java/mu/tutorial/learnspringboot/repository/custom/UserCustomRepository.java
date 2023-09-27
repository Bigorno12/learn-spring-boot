package mu.tutorial.learnspringboot.repository.custom;

import mu.tutorial.learnspringboot.entity.User;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserCustomRepository {

    List<User> findAllUsersByEqualIgnoreCase(@NonNull String name);
}
