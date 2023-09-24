package mu.tutorial.learnspringboot.mapper;

import mu.tutorial.learnspringboot.dto.UserDto;
import mu.tutorial.learnspringboot.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "address", source = "addressDto")
    @Mapping(target = "company", source = "companyDto")
    User mapToEntity(UserDto userDto);

    @InheritInverseConfiguration
    UserDto mapToDto(User user);
}
