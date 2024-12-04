package com.senials.meetmember.controller;

import com.senials.common.ResponseMessage;
import com.senials.common.mapper.UserMapper;
import com.senials.meet.repository.MeetRepository;
import com.senials.meetmember.repository.MeetMemberRepository;
import com.senials.meetmember.service.MeetMemberService;
import com.senials.user.dto.UserDTOForPublic;
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
public class MeetMemberController {


    private final UserMapper userMapper;
    private final MeetRepository meetRepository;
    private final MeetMemberRepository meetMemberRepository;
    private final MeetMemberService meetMemberService;

    public MeetMemberController(
            UserMapper userMapper
            , MeetRepository meetRepository
            , MeetMemberRepository meetMemberRepository
            , MeetMemberService meetMemberService
    ) {
        this.userMapper = userMapper;
        this.meetRepository = meetRepository;
        this.meetMemberRepository = meetMemberRepository;
        this.meetMemberService = meetMemberService;
    }

    /* 모임 일정 참여멤버 조회 */
    @GetMapping("/partyboards/{partyBoardNumber}/meets/{meetNumber}/meetmembers")
    public ResponseEntity<ResponseMessage> getMeetMembersByMeetNumber(
            @PathVariable Integer meetNumber
    ) {
        List<UserDTOForPublic> meetMemberList = meetMemberService.getMeetMembersByMeetNumber(meetNumber);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("meetMembers", meetMemberList);

        // ResponseHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "일정 참여멤버 조회 성공", responseMap));
    }

}
