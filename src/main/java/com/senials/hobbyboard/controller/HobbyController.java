package com.senials.hobbyboard.controller;

import com.senials.common.ResponseMessage;
import com.senials.config.HttpHeadersFactory;
import com.senials.favorites.entity.Favorites;
import com.senials.hobbyboard.dto.HobbyDTO;
import com.senials.hobbyboard.service.HobbyService;
import com.senials.hobbyreview.dto.HobbyReviewDTO;
import com.senials.hobbyreview.service.HobbyReviewService;
import com.senials.partyboard.dto.PartyBoardDTOForDetail;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class HobbyController {

    int userNumber=1;

    private HobbyService hobbyService;
    private final HttpHeadersFactory httpHeadersFactory;

    private HobbyReviewService hobbyReviewService;

    public HobbyController(HobbyService hobbyService,HobbyReviewService hobbyReviewService, HttpHeadersFactory httpHeadersFactory){

        this.hobbyService=hobbyService;
        this.hobbyReviewService=hobbyReviewService;
        this.httpHeadersFactory = httpHeadersFactory;
    }

    //취미 전체 조회
    @GetMapping("/hobby-board")
    public ResponseEntity<ResponseMessage> findHobbyAll(){

        HttpHeaders headers = httpHeadersFactory.createJsonHeaders();

        List<HobbyDTO> hobbyDTOList = hobbyService.findAll();

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("hobby", hobbyDTOList);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "조회 성공", responseMap));
    }

    @GetMapping("/hobby-board/top3")
    public ResponseEntity<ResponseMessage> hobbySortByRating(){

        HttpHeaders headers = httpHeadersFactory.createJsonHeaders();

        List<HobbyDTO> hobbyDTOList = hobbyService.hobbySortByRating(3,3);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("hobby", hobbyDTOList);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "조회 성공", responseMap));
    }

    //취미 상세 조회, 해당 취미후기 리스트 조회
    @GetMapping("/hobby-detail/{hobbyNumber}")
    public ResponseEntity<ResponseMessage> findHobbyDetail(@PathVariable("hobbyNumber")int hobbyNumber){

        HttpHeaders headers = httpHeadersFactory.createJsonHeaders();

        HobbyDTO hobbyDTO = hobbyService.findById(hobbyNumber);
        List<HobbyReviewDTO> hobbyReviewDTOList=hobbyReviewService.getReviewsListByHobbyNumber(hobbyNumber);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("hobbyReview",hobbyReviewDTOList);
        responseMap.put("hobby", hobbyDTO);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "조회 성공", responseMap));
    }

    //취미 카테고리별 취미 목록 조회
    @GetMapping("/hobby-board/{categoryNumber}")
    public ResponseEntity<ResponseMessage> findHobbyByCategory(@PathVariable("categoryNumber")int categoryNumber){

        HttpHeaders headers = httpHeadersFactory.createJsonHeaders();

        List<HobbyDTO> hobbyDTOList = hobbyService.findByCategory(categoryNumber);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("hobby", hobbyDTOList);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "조회 성공", responseMap));
    }

    //맞춤형 취미 추천 결과 조회
    @GetMapping("/suggest-hobby-result")
    public ResponseEntity<ResponseMessage> getSuggestHobby(@RequestParam int hobbyAbility,
                                                           @RequestParam int hobbyBudget,
                                                           @RequestParam int hobbyLevel,
                                                           @RequestParam int hobbyTendency) {

        HttpHeaders headers = httpHeadersFactory.createJsonHeaders();

        HobbyDTO hobbyDTO = hobbyService.suggestHobby(hobbyAbility, hobbyBudget,hobbyTendency, hobbyLevel);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("hobby", hobbyDTO);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(201, "생성 성공", responseMap));
    }

    //맞춤형 취미 추천 나의 취미 관심사 등록
    @PostMapping("/suggest-hobby-result")
    public ResponseEntity<ResponseMessage> setSuggestHobby(@RequestParam int hobbyNumber){

        HttpHeaders headers = httpHeadersFactory.createJsonHeaders();

        Favorites favorites=hobbyService.setFavoritesByHobby(hobbyNumber,userNumber);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("favorites",favorites);
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(201, "생성 성공", responseMap));

    }

    //맞춤형 취미 추천 관련 모임 조회
    @GetMapping("/partyboards/search/{hobbyNumber}")
    public ResponseEntity<ResponseMessage> getPartyBoardByHobbyNumber(@PathVariable("hobbyNumber") int hobbyNumber) {

        HttpHeaders headers = httpHeadersFactory.createJsonHeaders();

        List<PartyBoardDTOForDetail> partyBoardDTOList = hobbyService.getPartyBoardByHobbyNumber(hobbyNumber);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("party", partyBoardDTOList);
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(201, "생성 성공", responseMap));
    }
}
