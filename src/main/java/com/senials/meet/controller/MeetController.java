package com.senials.meet.controller;

import com.senials.common.ResponseMessage;
import com.senials.meet.dto.MeetDTO;
import com.senials.meet.service.MeetService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MeetController {

    private final MeetService meetService;


    public MeetController(MeetService meetService) {
        this.meetService = meetService;
    }


    /* 모임 내 일정 전체 조회 */
    @GetMapping("/partyboards/{partyBoardNumber}/meets")
    public ResponseEntity<ResponseMessage> getMeetsByPartyBoardNumber(
            @PathVariable Integer partyBoardNumber
    ) {

        List<MeetDTO> meetDTOList = meetService.getMeetsByPartyBoardNumber(partyBoardNumber);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("meets", meetDTOList);

        // ResponseHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "일정 전체 조회 완료", responseMap));
    }

    /* 모임 일정 추가 */
    @PostMapping("/partyboards/{partyBoardNumber}/meets")
    public ResponseEntity<ResponseMessage> registerMeet(
            @PathVariable Integer partyBoardNumber,
            @RequestBody MeetDTO meetDTO
    ) {
        meetService.registerMeet(partyBoardNumber, meetDTO);

        // ResponseHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "일정 추가 완료", null));
    }

        
    @GetMapping("/users/{userNumber}/meets")
    public ResponseEntity<List<MeetDTO>> getUserMeets(@PathVariable int userNumber) {
        List<MeetDTO> meets = meetService.getMeetsByUserNumber(userNumber);
        return ResponseEntity.ok(meets);
        
    }
}
