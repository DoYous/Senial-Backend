package com.senials.meetmember.repository;

import com.senials.meetmember.entity.MeetMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetMemberRepository extends JpaRepository<MeetMember, Integer> {
}
