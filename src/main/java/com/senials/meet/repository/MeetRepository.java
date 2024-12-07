package com.senials.meet.repository;

import com.senials.meet.entity.Meet;
import com.senials.partyboard.entity.PartyBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.List;

public interface MeetRepository extends JpaRepository<Meet, Integer> {

    /* 모임 내 일정 전체 조회 (내림차순) */
    List<Meet> findAllByPartyBoardOrderByMeetNumberDesc(PartyBoard partyBoard);
  
    //사용자 별 참여한 모임 확인
    @Query("SELECT m FROM Meet m JOIN m.partyBoard pb WHERE pb.user.userNumber = :userNumber")
    List<Meet> findAllByUserNumber(int userNumber);

    Meet findByPartyBoard(PartyBoard partyBoard);
}
