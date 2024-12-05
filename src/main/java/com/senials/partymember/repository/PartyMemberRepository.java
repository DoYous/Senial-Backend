package com.senials.partymember.repository;

import com.senials.partyboard.entity.PartyBoard;
import com.senials.partymember.entity.PartyMember;
import com.senials.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Integer> {

    List<PartyMember> findByUser_UserNumber(int userNumber); // 사용자별 참여 데이터 조회

  //페이지네이션 용
    Page<PartyMember> findByUser_UserNumber(int userNumber, Pageable pageable);

    List<PartyMember> findAllByPartyBoard(PartyBoard partyBoard);

    PartyMember findByPartyBoardAndUser(PartyBoard partyBoard, User user);
}
