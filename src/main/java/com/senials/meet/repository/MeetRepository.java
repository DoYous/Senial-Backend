package com.senials.meet.repository;

import com.senials.meet.entity.Meet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetRepository extends JpaRepository<Meet, Integer> {
}
