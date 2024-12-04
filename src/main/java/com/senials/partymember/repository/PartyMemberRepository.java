package com.senials.partymember.repository;

import com.senials.partymember.entity.PartyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Integer> {
}
