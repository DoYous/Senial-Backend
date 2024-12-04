package com.senials.user.service;

import com.senials.common.mapper.UserMapper;
import com.senials.partyboard.repository.PartyBoardRepository;
import com.senials.user.dto.UserCommonDTO;
import com.senials.user.dto.UserDTO;
import com.senials.user.entity.User;
import com.senials.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserMapper userMapper;

    private com.senials.user.repository.UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    // 특정 사용자 조회
    public UserCommonDTO getUserByNumber(int userNumber) {
        return userRepository.findById(userNumber)
                .map(user -> new UserCommonDTO(
                        user.getUserName(),
                        user.getUserNickname(),
                        user.getUserDetail(),
                        user.getUserProfileImg()
                ))
                .orElse(null);
    }
}
