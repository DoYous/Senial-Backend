package com.senials.hobbyboard.repository;

import com.senials.hobbyboard.entity.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Integer> {
    List<Hobby> findByCategoryNumber(int categoryNumber);
}
