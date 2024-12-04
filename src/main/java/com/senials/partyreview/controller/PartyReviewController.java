package com.senials.partyreview.controller;

import com.senials.common.ResponseMessage;
import com.senials.partyreview.dto.PartyReviewDTO;
import com.senials.partyreview.service.PartyReviewService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PartyReviewController {


    private final PartyReviewService partyReviewService;

    public PartyReviewController(PartyReviewService partyReviewService) {
        this.partyReviewService = partyReviewService;
    }

    /* 모임 후기 전체 조회*/
    @GetMapping("/partyboards/{partyBoardNumber}/partyreviews")
    public ResponseEntity<ResponseMessage> getPartyReviewsByPartyBoardNumber(
            @PathVariable Integer partyBoardNumber
    ) {
        List<PartyReviewDTO> partyReviewDTOList = partyReviewService.getPartyReviewsByPartyBoardNumber(partyBoardNumber);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("partyReviews", partyReviewDTOList);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "모임 후기 전체 조회 성공", responseMap));
    }

    /* 모임 후기 작성 */
    @PostMapping("/partyboards/{partyBoardNumber}/partyreviews")
    public ResponseEntity<ResponseMessage> registerPartyReview (
            @PathVariable Integer partyBoardNumber
            , @RequestBody PartyReviewDTO partyReviewDTO
    ) {
        // 유저 번호 임의 지정
        int userNumber = 4;

        partyReviewService.registerPartyReview(userNumber, partyBoardNumber, partyReviewDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "모임 후기 작성 성공", null));
    }


    /* 모임 후기 수정 */
    @PutMapping("/partyboards/{partyBoardNumber}/partyreviews/{partyReviewNumber}")
    public ResponseEntity<ResponseMessage> modifyPartyReview (
            @PathVariable Integer partyReviewNumber
            , @RequestBody PartyReviewDTO partyReviewDTO
    ) {

        partyReviewService.modifyPartyReview(partyReviewNumber, partyReviewDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "모임 후기 수정 성공", null));
    }

}
