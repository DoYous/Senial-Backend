package com.senials.partymember.repository;

import com.senials.partymember.entity.PartyMember;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Integer> {
    List<PartyMember> findByUser_UserNumber(int userNumber); // 사용자별 참여 데이터 조회
    Page<PartyMember> findByUser_UserNumber(int userNumber, Pageable pageable);

    /*사용자별 참여한 모임 개수*/
    long countByUser_UserNumber(int userNumber);
}
