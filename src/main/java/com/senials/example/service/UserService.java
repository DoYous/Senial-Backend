package com.senials.example.service;

import com.senials.example.dto.UserDTO;
import com.senials.example.entity.User;
import com.senials.common.mapper.UserMapper;
import com.senials.common.mapper.UserMapperImpl;
import com.senials.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserMapper userMapper;

    private UserRepository userRepository;

    // private ModelMapper

    public UserService(UserMapperImpl userMapperImpl, UserRepository userRepository) {
        this.userMapper = userMapperImpl;
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        List<User> userList = userRepository.findAll();

        List<UserDTO> userDTOList = userList.stream().map(user -> userMapper.toUserDTO(user)).toList();

        return userDTOList;
    }
}
