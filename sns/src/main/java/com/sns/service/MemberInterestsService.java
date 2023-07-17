package com.sns.service;

import java.security.Principal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sns.entity.Member;
import com.sns.entity.MemberInterests;
import com.sns.repository.MemberInterestsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberInterestsService {
	
	private final MemberInterestsRepository memberInterestsRepository;
	
	// 관심사 조회
	public MemberInterests loadMemberInterests(Member member) {
		MemberInterests memberInterests =  memberInterestsRepository.findByMember(member);
		return memberInterests;
	}
	
}
