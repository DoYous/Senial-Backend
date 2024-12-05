package com.senials.partyreview.repository;

import com.senials.partyboard.entity.PartyBoard;
import com.senials.partyreview.entity.PartyReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyReviewRepository extends JpaRepository<PartyReview, Integer> {

    List<PartyReview> findAllByPartyBoardOrderByPartyReviewWriteDateDesc(PartyBoard partyBoard);
}
