package mu.tutorial.learnspringboot.mapper;

import javax.annotation.processing.Generated;
import mu.tutorial.learnspringboot.dto.TodoDto;
import mu.tutorial.learnspringboot.dto.UserDto;
import mu.tutorial.learnspringboot.entity.Todo;
import mu.tutorial.learnspringboot.entity.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-24T20:38:29+0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Eclipse Adoptium)"
)
@Component
public class TodoMapperImpl implements TodoMapper {

    @Override
    public Todo mapToEntity(TodoDto todoDto) {
        if ( todoDto == null ) {
            return null;
        }

        Todo.TodoBuilder todo = Todo.builder();

        todo.user( userDtoToUser( todoDto.userDto() ) );
        todo.id( todoDto.id() );
        todo.title( todoDto.title() );
        todo.completed( todoDto.completed() );

        return todo.build();
    }

    @Override
    public TodoDto mapToDto(Todo todo) {
        if ( todo == null ) {
            return null;
        }

        TodoDto.TodoDtoBuilder todoDto = TodoDto.builder();

        todoDto.userDto( userToUserDto( todo.getUser() ) );
        todoDto.id( todo.getId() );
        todoDto.title( todo.getTitle() );
        todoDto.completed( todo.getCompleted() );

        return todoDto.build();
    }

    protected User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDto.id() );
        user.name( userDto.name() );
        user.username( userDto.username() );
        user.phone( userDto.phone() );
        user.website( userDto.website() );

        return user.build();
    }

    protected UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.name( user.getName() );
        userDto.username( user.getUsername() );
        userDto.phone( user.getPhone() );
        userDto.website( user.getWebsite() );

        return userDto.build();
    }
}
