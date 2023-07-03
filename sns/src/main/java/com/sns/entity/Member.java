package com.sns.entity;


import org.springframework.security.crypto.password.PasswordEncoder;

import com.sns.constant.Role;
import com.sns.dto.MemberFormDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long memberId;	// 회원식별자
	@Column(unique = true)
	private String email; // 회원이메일 (ID로사용)
	@Column(nullable = false)
	private String pasword; // 비밀번호
	@Column(nullable = false)
	private String nickname; // 닉네임
	@Enumerated(EnumType.STRING)
	private Role role; // ADMIN , USER
	@Column(nullable = true)
	private String memberTitle; // 자기소개 제목
	@Lob
	@Column(nullable = true)
	private String memberContent; // 자기소개 본문
	@Column(nullable = true)
	private String profileImeUrl; // 프로필사진
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		
		Member member = new Member();
		member.setEmail(memberFormDto.getEmail());
		member.setNickname(memberFormDto.getNickname());
		member.setPasword(password);
		member.setRole(Role.USER);
		
		return member;
	}
}
