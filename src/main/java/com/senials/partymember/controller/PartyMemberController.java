package com.senials.partymember.controller;

import com.senials.common.ResponseMessage;
import com.senials.partymember.service.PartyMemberService;
import com.senials.user.dto.UserDTOForPublic;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PartyMemberController {

    private final PartyMemberService partyMemberService;


    public PartyMemberController(
            PartyMemberService partyMemberService
    ) {
        this.partyMemberService = partyMemberService;
    }


    /* 모임 멤버 전체 조회 */
    @GetMapping("/partyboards/{partyBoardNumber}/partymembers")
    public ResponseEntity<ResponseMessage> getPartyMembers (
            @PathVariable Integer partyBoardNumber
    ) {
        List<UserDTOForPublic> userDTOForPublicList = partyMemberService.getPartyMembers(partyBoardNumber);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("partyMembers", userDTOForPublicList);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "모임 멤버 전체 조회 성공", responseMap));
    }

    /* 모임 참가 */
    @PostMapping("/partyboards/{partyBoardNumber}/partymembers")
    public ResponseEntity<ResponseMessage> registerPartyMember (
            @PathVariable Integer partyBoardNumber
    ) {
        // 유저 번호 임의 지정
        int userNumber = 2;

        partyMemberService.registerPartyMember(userNumber, partyBoardNumber);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "모임 가입 성공", null));
    }
}
