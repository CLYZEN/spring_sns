package com.sns.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sns.config.PrincipalDetail;
import com.sns.entity.Member;
import com.sns.repository.MemberRepository;

//@Service
public class RemembermeUserDetailService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(username);
		
		if(member.equals(null)) {
			throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.");
		}
		
		return new PrincipalDetail(member);
	}

}
