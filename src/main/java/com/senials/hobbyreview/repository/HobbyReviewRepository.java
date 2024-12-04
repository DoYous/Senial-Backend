package com.senials.hobbyreview.repository;

import com.senials.hobbyreview.entity.HobbyReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HobbyReviewRepository extends JpaRepository<HobbyReview,Integer> {
}
