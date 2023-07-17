package com.sns.repository;

import com.sns.entity.Member;
import com.sns.entity.MemberInterests;
import com.sns.id.MemberInterestsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInterestsRepository extends JpaRepository<MemberInterests, Long> {
	MemberInterests findByMember(Member member);
}
