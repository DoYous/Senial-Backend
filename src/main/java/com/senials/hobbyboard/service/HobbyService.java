package com.senials.hobbyboard.service;

import com.senials.common.mapper.HobbyMapper;
import com.senials.hobbyboard.dto.HobbyDTO;
import com.senials.hobbyboard.entity.Hobby;
import com.senials.hobbyboard.repository.HobbyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HobbyService {

    private final HobbyMapper hobbyMapper;
    private final HobbyRepository hobbyRepository;

    public HobbyService(HobbyRepository hobbyRepository, HobbyMapper hobbyMapper){
        this.hobbyRepository=hobbyRepository;
        this.hobbyMapper=hobbyMapper;
    }
    //전체 hobby 불러오기
    public List<HobbyDTO>findAll(){
        List<Hobby> hobbyList=hobbyRepository.findAll();

        List<HobbyDTO> hobbyDTOList=hobbyList.stream().map(hobby -> hobbyMapper.toHobbyDTO(hobby)).toList();

        return hobbyDTOList;
    }

    //특정 hobby hobbyNumber로 불러오기
    public HobbyDTO findById(int hobbyNumber){
        Hobby hobby=hobbyRepository.findById(hobbyNumber).orElseThrow(() -> new IllegalArgumentException("해당 취미가 존재하지 않습니다: " + hobbyNumber));
        HobbyDTO hobbyDTO=hobbyMapper.toHobbyDTO(hobby);

        return hobbyDTO;
    }

    //특정 hobby들 categoryNumber로 불러오기
    public List<HobbyDTO>findByCategory(int categoryNumber){
        List<Hobby> hobbyList=hobbyRepository.findByCategoryNumber(categoryNumber);

        List<HobbyDTO> hobbyDTOList=hobbyList.stream().map(hobby -> hobbyMapper.toHobbyDTO(hobby)).toList();

        return hobbyDTOList;
    }

}