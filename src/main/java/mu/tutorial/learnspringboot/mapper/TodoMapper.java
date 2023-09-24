package mu.tutorial.learnspringboot.mapper;

import mu.tutorial.learnspringboot.dto.TodoDto;
import mu.tutorial.learnspringboot.entity.Todo;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    @Mapping(target = "user", source = "userDto")
    Todo mapToEntity(TodoDto todoDto);

    @InheritInverseConfiguration
    TodoDto mapToDto(Todo todo);
}
