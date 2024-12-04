package com.senials.meetmember.service;

import com.senials.common.mapper.UserMapper;
import com.senials.meet.entity.Meet;
import com.senials.meet.repository.MeetRepository;
import com.senials.meetmember.entity.MeetMember;
import com.senials.meetmember.repository.MeetMemberRepository;
import com.senials.user.dto.UserDTOForPublic;
import com.senials.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetMemberService {

    private final UserMapper userMapper;
    private final MeetRepository meetRepository;
    private final MeetMemberRepository meetMemberRepository;

    public MeetMemberService(
            UserMapper userMapper,
            MeetRepository meetRepository
            , MeetMemberRepository meetMemberRepository
    ) {
        this.userMapper = userMapper;
        this.meetRepository = meetRepository;
        this.meetMemberRepository = meetMemberRepository;
    }

    /* 모임 일정 참여멤버 조회 */
    public List<UserDTOForPublic> getMeetMembersByMeetNumber(int meetNumber) {

        Meet meet = meetRepository.findById(meetNumber)
                .orElseThrow(IllegalArgumentException::new);

        /* 일정 참여 멤버 리스트 도출 */
        List<MeetMember> meetMemberList = meetMemberRepository.findAllByMeet(meet);

        /* 멤버 리스트 -> 유저 정보 도출 */
        List<User> userList = meetMemberList.stream()
                .map(meetMember -> meetMember.getPartyMember().getUser())
                .toList();

        /* DTO로 변환 */
        List<UserDTOForPublic> userDTOForPublicList = userList.stream()
                .map(userMapper::toUserDTOForPublic)
                .toList();

        return userDTOForPublicList;
    }
}
