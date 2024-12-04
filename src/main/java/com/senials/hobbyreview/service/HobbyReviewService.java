package com.senials.hobbyreview.service;
import com.senials.common.mapper.HobbyReviewMapper;
import com.senials.hobbyboard.entity.Hobby;
import com.senials.hobbyboard.repository.HobbyRepository;
import com.senials.hobbyreview.dto.HobbyReviewDTO;
import com.senials.hobbyreview.entity.HobbyReview;
import com.senials.hobbyreview.repository.HobbyReviewRepository;
import com.senials.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HobbyReviewService {
    private final HobbyReviewMapper hobbyReviewMapper;
    private final HobbyReviewRepository hobbyReviewRepository;
    private final HobbyRepository hobbyRepository;
    private final UserRepository userRepository;

    HobbyReviewService(HobbyReviewRepository hobbyReviewRepository, HobbyReviewMapper hobbyReviewMapper, HobbyRepository hobbyRepository, UserRepository userRepository){
        this.hobbyReviewMapper=hobbyReviewMapper;
        this.hobbyReviewRepository=hobbyReviewRepository;
        this.hobbyRepository=hobbyRepository;
        this.userRepository=userRepository;
    }

    //취미 번호가 같은 취미리뷰 리스트 불러오기
    public List<HobbyReviewDTO> getReviewsListByHobbyNumber(int hobbyNumber) {
        Hobby hobby = hobbyRepository.findById(hobbyNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 취미 번호가 존재하지 않습니다: " + hobbyNumber));
        List<HobbyReview> hobbyReviewList=hobbyReviewRepository.findByHobby(hobby);
        List<HobbyReviewDTO> hobbyReviewDTOList=hobbyReviewList.stream().map(hobbyReview -> {
            HobbyReviewDTO dto=hobbyReviewMapper.toHobbyReviewDTO(hobbyReview);
            dto.setHobbyNumber(hobbyReview.getHobby().getHobbyNumber());
            dto.setUserNumber(hobbyReview.getUser().getUserNumber());
            dto.setUserName(hobbyReview.getUser().getUserName());
            return dto;
        }).toList();
        return hobbyReviewDTOList;
    }
}
