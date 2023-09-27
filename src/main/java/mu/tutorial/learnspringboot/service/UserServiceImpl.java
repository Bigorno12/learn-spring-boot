package mu.tutorial.learnspringboot.service;

import lombok.extern.slf4j.Slf4j;
import mu.tutorial.learnspringboot.dto.UserDto;
import mu.tutorial.learnspringboot.entity.User;
import mu.tutorial.learnspringboot.exception.UserException;
import mu.tutorial.learnspringboot.mapper.UserMapper;
import mu.tutorial.learnspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserDto> retrieveUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::mapToDto);
    }

    @Override
    public UserDto updateUserEntity(UserDto userDto) {

        User updatedUser = userMapper.mapToEntity(userDto);
        User userInDB = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new UserException("User not found"));

        userRepository.deleteById(userInDB.getId());
        userRepository.save(updatedUser);

        return userMapper.mapToDto(updatedUser);
    }

    @Override
    public List<UserDto> retrieveUsersByName(String name) {
        return userRepository.findAllUsersByEqualIgnoreCase(name)
                .stream()
                .map(userMapper::mapToDto)
                .toList();
    }

}
