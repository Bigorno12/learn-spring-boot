package mu.tutorial.learnspringboot.mapper;

import mu.tutorial.learnspringboot.entity.User;
import mu.tutorial.learnspringboot.model.UserDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToEntity(UserDto userDto);

    @InheritInverseConfiguration
    UserDto mapToDto(User user);
}
