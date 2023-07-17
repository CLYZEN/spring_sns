package com.sns.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sns.entity.Member;
import com.sns.entity.MemberInterests;
import com.sns.repository.MemberInterestsRepository;
import com.sns.repository.MemberRepository;


import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{

	private final MemberRepository memberRepository;
	private final MemberInterestsRepository memberInterestsRepository;
	
	public Member saveMember(Member member) {
		validateDuplicateMember(member);
		
		Member savedMember = memberRepository.save(member);
		return savedMember;
	}
	
	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findByEmail(member.getEmail());
		
		if(findMember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(email);
		
		if (member == null ) {
			throw new UsernameNotFoundException(email);
		}
		
		return User.builder()
				.username(member.getEmail())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
	}
	
	// 진행중	
	public MemberInterests loadMemberInterests(String email) {
		Member member = memberRepository.findByEmail(email);
		MemberInterests memberInterests = memberInterestsRepository.findByMember(member);
		
		return memberInterests;
	}
	
}
