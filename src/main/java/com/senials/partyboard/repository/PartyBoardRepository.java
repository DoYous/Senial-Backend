package com.senials.partyboard.repository;

import com.senials.partyboard.entity.PartyBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyBoardRepository extends JpaRepository<PartyBoard, Integer>, JpaSpecificationExecutor<PartyBoard> {
}
