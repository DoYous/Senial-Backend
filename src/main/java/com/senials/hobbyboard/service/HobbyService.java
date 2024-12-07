package com.senials.hobbyboard.service;

import com.senials.common.mapper.HobbyMapper;
import com.senials.common.mapper.PartyBoardMapper;
import com.senials.favorites.entity.Favorites;
import com.senials.favorites.repository.FavoritesRepository;
import com.senials.hobbyboard.dto.HobbyDTO;
import com.senials.hobbyboard.entity.Hobby;
import com.senials.hobbyboard.repository.HobbyRepository;
import com.senials.hobbyreview.repository.HobbyReviewRepository;
import com.senials.partyboard.dto.PartyBoardDTOForDetail;
import com.senials.partyboard.entity.PartyBoard;
import com.senials.partyboard.repository.PartyBoardRepository;
import com.senials.user.entity.User;
import com.senials.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class HobbyService {

    private final HobbyMapper hobbyMapper;
    private final HobbyRepository hobbyRepository;
    private final HobbyReviewRepository hobbyReviewRepository;
    private final UserRepository userRepository;
    private final FavoritesRepository favoritesRepository;
    private final PartyBoardRepository partyBoardRepository;
    private final PartyBoardMapper partyBoardMapper;

    public HobbyService(HobbyRepository hobbyRepository,
                        HobbyMapper hobbyMapper,
                        UserRepository userRepository,
                        FavoritesRepository favoritesRepository,
                        PartyBoardRepository partyBoardRepository,
                        PartyBoardMapper partyBoardMapper,
                        HobbyReviewRepository hobbyReviewRepository) {
        this.hobbyRepository = hobbyRepository;
        this.hobbyMapper = hobbyMapper;
        this.userRepository = userRepository;
        this.favoritesRepository = favoritesRepository;
        this.partyBoardRepository=partyBoardRepository;
        this.partyBoardMapper=partyBoardMapper;
        this.hobbyReviewRepository=hobbyReviewRepository;
    }

    //전체 hobby 불러오기
    public List<HobbyDTO> findAll() {
        List<Hobby> hobbyList = hobbyRepository.findAll();
        List<Object[]> rate = hobbyReviewRepository.findAverageReviewRateByHobby();

        Map<Integer, Double> hobbyReviewRateMap = rate.stream()
                .collect(Collectors.toMap(
                        result -> (Integer) result[0],  // hobby_number
                        result -> ((BigDecimal) result[1]).doubleValue()  //평균 평균
                ));

        List<HobbyDTO> hobbyDTOList = hobbyList.stream().map(hobby -> {
            HobbyDTO dto=hobbyMapper.toHobbyDTO(hobby);
            dto.setRating(hobbyReviewRateMap.getOrDefault(hobby.getHobbyNumber(), 0.0));
            return dto;
        }).toList();

        return hobbyDTOList;
    }

    //특정 hobby hobbyNumber로 불러오기
    public HobbyDTO findById(int hobbyNumber) {
        Hobby hobby = hobbyRepository.findById(hobbyNumber).orElseThrow(() -> new IllegalArgumentException("해당 취미가 존재하지 않습니다: " + hobbyNumber));
        List<Object[]> rate = hobbyReviewRepository.findAverageReviewRateByHobby();

        Double rating = rate.stream()
                .filter(result -> (Integer) result[0] == hobbyNumber)  // hobbyNumber가 일치하는 항목 찾기
                .map(result -> ((BigDecimal) result[1]).doubleValue()) // 해당 평점을 가져오기
                .findFirst()
                .orElse(0.0);

        HobbyDTO hobbyDTO = hobbyMapper.toHobbyDTO(hobby);
        hobbyDTO.setRating(rating);

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

    //사용자와 취미를 받아와 해당 사용자에게 해당 취미를 관심사로 부여
    public Favorites setFavoritesByHobby(int hobbyNumber, int userNumber){
        User user= userRepository.findById(userNumber).orElseThrow(() -> new IllegalArgumentException("해당 유저 번호가 존재하지 않습니다: " + userNumber));
        Hobby hobby=hobbyRepository.findById(hobbyNumber).orElseThrow(() -> new IllegalArgumentException("해당 취미 번호가 존재하지 않습니다: " + hobbyNumber));
        Favorites favoritesEntity = new Favorites();
        favoritesEntity.initializeHobby(hobby);
        favoritesEntity.initializeUser(user);

        Favorites favorites=favoritesRepository.save(favoritesEntity);

        return favorites;
    }

    //취미 번호를 통해 해당하는 모임리스트 조회
    public List<PartyBoardDTOForDetail> getPartyBoardByHobbyNumber(int hobbyNumber) {
        Hobby hobby=hobbyRepository.findById(hobbyNumber).orElseThrow(() -> new IllegalArgumentException("해당 취미 번호가 존재하지 않습니다: " + hobbyNumber));;
        List<PartyBoard> partyBoardList=partyBoardRepository.findByHobby(hobby);
        List<PartyBoardDTOForDetail> partyBoardDTOList=partyBoardList.stream().map(partyBoardMapper::toPartyBoardDTOForDetail).toList();

        return partyBoardDTOList;
    }

}
