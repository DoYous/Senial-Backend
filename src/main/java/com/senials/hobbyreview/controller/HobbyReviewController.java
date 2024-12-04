package com.senials.hobbyreview.controller;

import com.senials.common.ResponseMessage;
import com.senials.config.HttpHeadersFactory;
import com.senials.hobbyreview.dto.HobbyReviewDTO;
import com.senials.hobbyreview.entity.HobbyReview;
import com.senials.hobbyreview.service.HobbyReviewService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HobbyReviewController {

    int userNumber=1;

    private final HobbyReviewService hobbyReviewService;

    private final HttpHeadersFactory httpHeadersFactory;

    public HobbyReviewController(HobbyReviewService hobbyReviewService, HttpHeadersFactory httpHeadersFactory){
        this.hobbyReviewService=hobbyReviewService;
        this.httpHeadersFactory=httpHeadersFactory;
    }

    //취미 후기 조회
    @GetMapping("{hobbyNumber}/hobby-review/{hobbyReviewNumber}")
    public ResponseEntity<ResponseMessage> getHobbyReview(
            @PathVariable("hobbyNumber") int hobbyNumber,
            @PathVariable("hobbyReviewNumber") int hobbyReviewNumber) {

        HttpHeaders headers = httpHeadersFactory.createJsonHeaders();

       HobbyReviewDTO hobbyReviewDTO= hobbyReviewService.getHobbyReview(hobbyNumber,userNumber,hobbyReviewNumber);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("hobbyReview",hobbyReviewDTO);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "조회 성공", responseMap));
    }

    //취미 후기 작성
    @PostMapping("/{hobbyNumber}/hobby-review")
    public ResponseEntity<ResponseMessage> createHobbyReview(@RequestBody HobbyReviewDTO hobbyReviewDTO, @PathVariable("hobbyNumber")int hobbyNumber) {

        HttpHeaders headers = httpHeadersFactory.createJsonHeaders();

        HobbyReview hobbyReview= hobbyReviewService.saveHobbyReview(hobbyReviewDTO,userNumber,hobbyNumber);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("hobbyReview",hobbyReview);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(201, "생성 성공", responseMap));
    }

    //취미 후기 삭제
    @DeleteMapping("/{hobbyNumber}/hobby-review/{hobbyReviewNumber}")
    public ResponseEntity<ResponseMessage> deleteHobbyReview(
            @PathVariable("hobbyNumber") int hobbyNumber,
            @PathVariable("hobbyReviewNumber") int hobbyReviewNumber) {
        try {
            hobbyReviewService.deleteHobbyReview(hobbyReviewNumber, userNumber, hobbyNumber);

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("message", "삭제 성공");

            return ResponseEntity.ok()
                    .headers(httpHeadersFactory.createJsonHeaders())
                    .body(new ResponseMessage(204, "삭제 성공", responseMap));
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("message", e.getMessage());

            return ResponseEntity.badRequest()
                    .headers(httpHeadersFactory.createJsonHeaders())
                    .body(new ResponseMessage(400, "삭제 실패", errorMap));
        }
    }

    //취미 후기 수정
    @PutMapping("{hobbyNumber}/hobby-review/{hobbyReviewNumber}")
    public ResponseEntity<ResponseMessage> updateHobbyReview(
            @PathVariable("hobbyNumber") int hobbyNumber,
            @PathVariable("hobbyReviewNumber") int hobbyReviewNumber,
            @RequestBody HobbyReviewDTO hobbyReviewDTO) {
        try {
            hobbyReviewService.updateHobbyReview(hobbyReviewDTO,hobbyReviewNumber, userNumber, hobbyNumber);

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("message", "수정 성공");

            return ResponseEntity.ok()
                    .headers(httpHeadersFactory.createJsonHeaders())
                    .body(new ResponseMessage(200, "수정 성공", responseMap));
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("message", e.getMessage());

            return ResponseEntity.badRequest()
                    .headers(httpHeadersFactory.createJsonHeaders())
                    .body(new ResponseMessage(400, "수정 실패", errorMap));
        }
    }

}