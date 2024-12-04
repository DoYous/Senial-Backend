package com.senials.partyboard.repository;

import com.senials.hobbyboard.entity.Hobby;
import com.senials.partyboard.dto.PartyBoardDTO;
import com.senials.partyboard.entity.PartyBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyBoardRepository extends JpaRepository<PartyBoard, Integer>, JpaSpecificationExecutor<PartyBoard> {

    // 모임 상세 조회
    PartyBoard findByPartyBoardNumber(int partyBoardNumber);

    Page<PartyBoard> findAllByHobbyIn(List<Hobby> hobby, Pageable pageable);

    List<PartyBoard> findByHobby(Hobby hobby);

}
