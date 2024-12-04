package com.senials.partyreview.controller;

import com.senials.common.ResponseMessage;
import com.senials.partyreview.dto.PartyReviewDTO;
import com.senials.partyreview.service.PartyReviewService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
