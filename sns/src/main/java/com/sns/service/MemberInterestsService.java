package com.sns.service;

import java.security.Principal;

import com.sns.dto.MemberInterestsDto;
import com.sns.repository.MemberRepository;
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
	private final MemberService memberService;
	private final MemberRepository memberRepository;

	// 관심사 조회
	public MemberInterests loadMemberInterests(String email) {
		Member member = memberRepository.findByEmail(email);

		MemberInterests memberInterests =  memberInterestsRepository.findByMember(member);
		return memberInterests;
	}

	// 회원 관심사 Insert
	public void insertMemberInterests(String email, MemberInterests memberInterests) {
		Member member = memberRepository.findByEmail(email);

		memberInterests.setMember(member);

		memberInterestsRepository.save(memberInterests);
	}

	public void updateMemberInterests(MemberInterestsDto memberInterestsDto, Member member) {
		MemberInterests memberInterests = memberInterestsRepository.findByMember(member);

		memberInterests.updateMemberInterests(memberInterestsDto);
	}
}
