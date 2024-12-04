package com.senials.common.mapper;


import com.senials.user.dto.UserDTO;
import com.senials.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    // User -> UserDTO
    UserDTO toUserDTO(User user);

}
