package com.senials.meetmember.service;

import com.senials.common.mapper.UserMapper;
import com.senials.meet.entity.Meet;
import com.senials.meet.repository.MeetRepository;
import com.senials.meetmember.entity.MeetMember;
import com.senials.meetmember.repository.MeetMemberRepository;
import com.senials.partyboard.entity.PartyBoard;
import com.senials.partyboard.repository.PartyBoardRepository;
import com.senials.partymember.entity.PartyMember;
import com.senials.partymember.repository.PartyMemberRepository;
import com.senials.user.dto.UserDTOForPublic;
import com.senials.user.entity.User;
import com.senials.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MeetMemberService {

    private final UserMapper userMapper;
    private final MeetRepository meetRepository;
    private final MeetMemberRepository meetMemberRepository;
    private final PartyMemberRepository partyMemberRepository;
    private final PartyBoardRepository partyBoardRepository;
    private final UserRepository userRepository;

    public MeetMemberService(
            UserMapper userMapper
            , MeetRepository meetRepository
            , MeetMemberRepository meetMemberRepository
            , PartyMemberRepository partyMemberRepository,
            PartyBoardRepository partyBoardRepository, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.meetRepository = meetRepository;
        this.meetMemberRepository = meetMemberRepository;
        this.partyMemberRepository = partyMemberRepository;
        this.partyBoardRepository = partyBoardRepository;
        this.userRepository = userRepository;
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

    /* 모임 일정 참여 */
    public void joinMeetMembers(Integer userNumber, Integer partyBoardNumber, Integer meetNumber) {

        User user = userRepository.findById(userNumber)
                .orElseThrow(() -> new IllegalArgumentException("서버 내부 오류"));


        /* 일정이 존재하는지 */
        Meet meet = meetRepository.findById(meetNumber)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청 (일정이 존재하지 않음)"));


        /* 해당 모임에 가입되어 있는 지 (PathVariable 불필요) */
        PartyBoard partyBoard = meet.getPartyBoard();
        PartyMember partyMember = partyMemberRepository.findByPartyBoardAndUser(partyBoard, user);
        if(partyMember == null) {
            throw new IllegalArgumentException("잘못된 요청 (모임에 속하지 않음)");
        }


        /* 신청 시점이 시작일을 넘겼는 지 */
        LocalDate startDate = meet.getMeetStartDate();
        LocalDate presentDate = LocalDate.now();
        if(presentDate.isAfter(startDate) || presentDate.isEqual(startDate)) {
            throw new IllegalArgumentException("이미 종료(시작)된 일정");
        }


        /* 이미 일정 참여 멤버 인지 (Unique 제약조건으로 곧바로 save 성공 여부로 단축 가능)*/
        MeetMember meetMember = meetMemberRepository.findByMeetAndPartyMember(meet, partyMember);

        if(meetMember == null) {
            meetMemberRepository.save(new MeetMember(0, meet, partyMember));
        } else {
            throw new IllegalArgumentException("잘못된 요청 (이미 참여 중인 멤버)");
        }

    }

    /* 모임 일정 탈퇴 */
    public void quitMeetMembers(Integer userNumber, Integer partyBoardNumber, Integer meetNumber) {

        User user = userRepository.findById(userNumber)
                .orElseThrow(() -> new IllegalArgumentException("서버 내부 오류"));


        /* 일정이 존재하는지 */
        Meet meet = meetRepository.findById(meetNumber)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청 (일정이 존재하지 않음)"));


        /* 해당 모임에 가입되어 있는 지 (PathVariable 불필요) */
        PartyBoard partyBoard = meet.getPartyBoard();
        PartyMember partyMember = partyMemberRepository.findByPartyBoardAndUser(partyBoard, user);
        if(partyMember == null) {
            throw new IllegalArgumentException("잘못된 요청 (모임에 속하지 않음)");
        }


        /* 이미 일정에 참여하지 않은 상태인지 */
        MeetMember meetMember = meetMemberRepository.findByMeetAndPartyMember(meet, partyMember);

        if(meetMember == null) {
            throw new IllegalArgumentException("잘못된 요청 (일정 참여 멤버가 아님)");
        }


        /* 탈퇴 시점이 시작일로부터 이틀 이내인지 혹은 시작일을 넘겼는 지 */
        LocalDate startDate = meet.getMeetStartDate();
        LocalDate deadLine = startDate.minusDays(2);
        LocalDate presentDate = LocalDate.now();
        if(presentDate.isAfter(deadLine) || presentDate.isEqual(deadLine)) {

            throw new IllegalArgumentException("일정 시작일로부터 최소 이틀 전 탈퇴 가능 (모임장에 문의)");

        }else if(presentDate.isAfter(startDate) || presentDate.isEqual(startDate)) {

            throw new IllegalArgumentException("이미 종료(시작)된 일정");

        }


        meetMemberRepository.delete(meetMember);

    }
}
