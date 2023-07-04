package com.sns.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "social_login")
public class SocialLogin extends BaseTimeEntity {
	
	@Id
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@Column(nullable = false)
	private String email; // 이메일
	
	@Column(nullable = false)
	private String platform; // 플랫폼
	
	@Column(nullable = false)
	private String acsessToken; // 토큰
}
