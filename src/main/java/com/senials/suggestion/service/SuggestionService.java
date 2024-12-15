package com.senials.suggestion.service;

import com.senials.common.mapper.SuggestionMapper;
import com.senials.suggestion.dto.SuggestionDTO;
import com.senials.suggestion.entity.Suggestion;
import com.senials.suggestion.repository.SuggestionRepository;
import com.senials.user.entity.User;
import com.senials.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuggestionService {

    private final SuggestionRepository suggestionRepository;
    private final SuggestionMapper suggestionMapper;
    private final UserRepository userRepository;

    public SuggestionService(SuggestionRepository suggestionRepository,
                             SuggestionMapper suggestionMapper,
                             UserRepository userRepository) {
        this.suggestionRepository = suggestionRepository;
        this.suggestionMapper = suggestionMapper;
        this.userRepository = userRepository;
    }

    //건의사항을 생성
    public Suggestion saveSuggestion(SuggestionDTO suggestionDTO){
        User user=userRepository.findById(suggestionDTO.getUserNumber()).orElseThrow(()->new IllegalArgumentException("해당 유저 번호가 존재하지 않습니다: "));
        Suggestion suggestion=suggestionMapper.toSuggestionEntity(suggestionDTO);
        suggestion.initializeUser(user);
        Suggestion saveSuggestion=suggestionRepository.save(suggestion);

        return saveSuggestion;
    }

    //건의 전체 조회
    public List<SuggestionDTO> getSuggestionList() {
        List<Suggestion> suggestionList = suggestionRepository.findAll();
        List<SuggestionDTO> suggestionDTOList = suggestionList.stream()
                .map(suggestion ->suggestionMapper.toSuggestionDTO(suggestion))
                .collect(Collectors.toList());
        return suggestionDTOList;
    }
}
