package com.sns.controller;

import com.sns.dto.MemberInterestsDto;
import com.sns.dto.ProfilePostDto;
import com.sns.entity.MemberInterests;
import com.sns.service.MemberInterestsService;
import com.sns.service.PostService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sns.dto.MemberFormDto;
import com.sns.entity.Member;
import com.sns.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final MemberInterestsService memberInterestsService;
	private final PostService postService;

	@GetMapping(value="/members/login")
	public String login(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/login";
	}
	
	// 로그인 실패했을 때 
	@GetMapping(value = "/members/login/error")
	public String loginError(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "member/login";
	}
	
	@PostMapping(value = "/members/register")
	public String register(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		
		if( bindingResult.hasErrors()) {
			model.addAttribute("registerChk","Check");
			return "member/login";
		}
		
		try {
			
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
		} catch (Exception e) {
			model.addAttribute("registerChk","Check");
			model.addAttribute("errorMessage",e.getMessage());
			return "member/login";
		}
		
		return "member/selectMemberInterests";
	}
	
	@GetMapping(value ="/myProfile")
	public String myProfile(Principal principal, Model model) {
		MemberInterests memberInterests = memberInterestsService.loadMemberInterests(principal.getName());
		Member member = memberService.findByEmail(principal.getName());

		List<ProfilePostDto> profilePostDtoList = postService.findMyPost(principal.getName());

		model.addAttribute("posts", profilePostDtoList);
		model.addAttribute("member",member);
		model.addAttribute("memberInterests", memberInterests);

		return "member/myProfile";
	}
	
	@GetMapping(value = "/myProfileModify")
	public String myProfileModify() {
		return "member/myProfileModify";
	}
	@GetMapping(value = "/interests")
	public String selectInterests(Model model) {
		model.addAttribute("memberInterestsDto", new MemberInterestsDto());

		return "member/selectMemberInterests";
	}
	@PostMapping(value = "/interests")
	public String insertInterests(Principal principal, @Valid MemberInterestsDto memberInterestsDto) {

		MemberInterests memberInterests = MemberInterests.createMemberInterests(memberInterestsDto);

		memberInterestsService.insertMemberInterests(principal.getName(),memberInterests);

		return "redirect:/main";
	}
}
