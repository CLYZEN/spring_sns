package com.sns.service;

import com.sns.dto.MemberInterestsDto;
import com.sns.dto.ProfileFormDto;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{

	@Value("${profileImgLocation}")
	private String profileImgLocation;
	private final MemberRepository memberRepository;
	private final MemberInterestsRepository memberInterestsRepository;
	private final FileService fileService;

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

	public Member findByEmail(String email) {

		Member member = memberRepository.findByEmail(email);

		return member;
	}

	public void updateProfile(ProfileFormDto profileFormDto, MultipartFile profileImgFile,String email) throws Exception {
		String oriImgName = profileImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";

		Member member = memberRepository.findByEmail(email);

		if(!StringUtils.isEmpty(oriImgName)) {
			//oriImgName이 빈문자열이 아니라면 이미지 파일 업로드
			imgName = fileService.uploadFile(profileImgLocation,
					oriImgName, profileImgFile.getBytes());
			imgUrl = "/images/profile/" + imgName;

			profileFormDto.setProfileImgUrl(imgUrl);
			member.updateMember(profileFormDto);
			return;
		}
		member.updateMember(profileFormDto);

	}

	public Member findById(Long memberId) {
		return memberRepository.findById(memberId).orElseThrow();
	}



}
