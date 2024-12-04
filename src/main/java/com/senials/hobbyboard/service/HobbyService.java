package com.senials.hobbyboard.service;

import com.senials.common.mapper.HobbyMapper;
import com.senials.common.mapper.HobbyReviewMapper;
import com.senials.hobbyboard.dto.HobbyDTO;
import com.senials.hobbyboard.entity.Hobby;
import com.senials.hobbyboard.repository.HobbyRepository;
import com.senials.hobbyreview.dto.HobbyReviewDTO;
import com.senials.hobbyreview.entity.HobbyReview;
import com.senials.hobbyreview.repository.HobbyReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class HobbyService {

    private final HobbyMapper hobbyMapper;
    private final HobbyRepository hobbyRepository;
    private final HobbyReviewMapper hobbyReviewMapper;
    private final HobbyReviewRepository hobbyReviewRepository;

    public HobbyService(HobbyRepository hobbyRepository, HobbyMapper hobbyMapper, HobbyReviewRepository hobbyReviewRepository, HobbyReviewMapper hobbyReviewMapper) {
        this.hobbyRepository = hobbyRepository;
        this.hobbyMapper = hobbyMapper;
        this.hobbyReviewRepository = hobbyReviewRepository;
        this.hobbyReviewMapper = hobbyReviewMapper;
    }

    //전체 hobby 불러오기
    public List<HobbyDTO> findAll() {
        List<Hobby> hobbyList = hobbyRepository.findAll();

        List<HobbyDTO> hobbyDTOList = hobbyList.stream().map(hobby -> hobbyMapper.toHobbyDTO(hobby)).toList();

        return hobbyDTOList;
    }

    //특정 hobby hobbyNumber로 불러오기
    public HobbyDTO findById(int hobbyNumber) {
        Hobby hobby = hobbyRepository.findById(hobbyNumber).orElseThrow(() -> new IllegalArgumentException("해당 취미가 존재하지 않습니다: " + hobbyNumber));
        HobbyDTO hobbyDTO = hobbyMapper.toHobbyDTO(hobby);

        return hobbyDTO;
    }

    //특정 hobby들 categoryNumber로 불러오기
    public List<HobbyDTO> findByCategory(int categoryNumber) {
        List<Hobby> hobbyList = hobbyRepository.findByCategoryNumber(categoryNumber);

        List<HobbyDTO> hobbyDTOList = hobbyList.stream().map(hobby -> hobbyMapper.toHobbyDTO(hobby)).toList();

        return hobbyDTOList;
    }

    //4가지 요소를 우선순위로 둬서 최종적으로 하나의 hobby를 조회
    public HobbyDTO suggestHobby(int hobbyAbility, int hobbyBudget, int hobbyLevel, int hobbyTendency) {
        List<Hobby> hobbyList = null;
        List<Hobby> tempList = hobbyRepository.findByHobbyAbility(hobbyAbility);
        if (!tempList.isEmpty()) {
            hobbyList = tempList;
            tempList = tempList.stream()
                    .filter(hobby -> hobby.getHobbyBudget() == hobbyBudget)
                    .collect(Collectors.toList());
            if (!tempList.isEmpty()) {
                hobbyList = tempList;
                tempList = tempList.stream()
                        .filter(hobby -> hobby.getHobbyBudget() == hobbyTendency)
                        .collect(Collectors.toList());
                if (!tempList.isEmpty()) {
                    hobbyList = tempList;
                    tempList = tempList.stream()
                            .filter(hobby -> hobby.getHobbyBudget() == hobbyLevel)
                            .collect(Collectors.toList());
                    if (!tempList.isEmpty()) {
                        hobbyList = tempList;
                    }
                }
            }
        }
        List<HobbyDTO> hobbyDTOList = hobbyList.stream().map(hobby -> hobbyMapper.toHobbyDTO(hobby)).toList();

        Random random = new Random();
        int randomIndex = random.nextInt(hobbyDTOList.size());
        HobbyDTO hobbyDTO = hobbyDTOList.get(randomIndex);

        return hobbyDTO;
    }

}
