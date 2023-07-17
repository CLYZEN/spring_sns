package com.sns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sns.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByEmail(String email);
}
