package com.senials.common.mapper;

import com.senials.hobbyreview.dto.HobbyReviewDTO;
import com.senials.hobbyreview.entity.HobbyReview;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HobbyReviewMapper {

    HobbyReviewDTO toHobbyReviewDTO(HobbyReview hobbyReview);

    HobbyReview toHobbyReviewEntity(HobbyReviewDTO hobbyReviewDTO);

}
