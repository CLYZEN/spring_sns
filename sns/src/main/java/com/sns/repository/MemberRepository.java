package com.sns.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sns.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByEmail(String email);
}
