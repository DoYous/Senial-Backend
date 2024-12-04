package com.senials.partyboardimage.repository;

import com.senials.partyboardimage.entity.PartyBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyBoardImageRepository extends JpaRepository<PartyBoardImage, Integer> {
}
