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

    public List<HobbyDTO>findAll(){
        List<Hobby> hobbyList=hobbyRepository.findAll();

        List<HobbyDTO> hobbyDTOList=hobbyList.stream().map(hobby -> hobbyMapper.toHobbyDTO(hobby)).toList();

        return hobbyDTOList;
    }

}
