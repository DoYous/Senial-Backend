package com.senials.common.mapper;

import com.senials.hobbyboard.entity.Hobby;
import com.senials.hobbyboard.dto.HobbyDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HobbyMapper {
    HobbyDTO toHobbyDTO(Hobby hobby);

}
