package com.sns.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {
	@NotBlank(message = "닉네임은 필수 입력 값입니다.")
	private String nickname; // 닉네임
	
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	private String email;
	
	@Length(min = 8, max = 16, message = "8자 ~ 16자 사이로 입력해주세요.")
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	private String password;
}
