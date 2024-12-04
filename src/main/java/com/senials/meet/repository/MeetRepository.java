package com.senials.meet.repository;

import com.senials.meet.entity.Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeetRepository extends JpaRepository<Meet, Integer> {

    //사용자 별 참여한 모임 확인
    @Query("SELECT m FROM Meet m JOIN m.partyBoard pb WHERE pb.user.userNumber = :userNumber")
    List<Meet> findAllByUserNumber(int userNumber);
}
