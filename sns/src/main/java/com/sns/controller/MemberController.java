package com.sns.controller;

import com.sns.dto.*;
import com.sns.entity.MemberInterests;
import com.sns.repository.MemberRepository;
import com.sns.service.MemberInterestsService;
import com.sns.service.PostImageService;
import com.sns.service.PostService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.sns.entity.Member;
import com.sns.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final MemberInterestsService memberInterestsService;
	private final PostService postService;
	private final PostImageService postImageService;

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
		List<PostImgDto> postImgDtoList = postImageService.findMyImages(member.getMemberId());

		model.addAttribute("myImages", postImgDtoList);
		model.addAttribute("posts", profilePostDtoList);
		model.addAttribute("member",member);
		model.addAttribute("memberInterests", memberInterests);

		return "member/myProfile";
	}
	
	@GetMapping(value = "/myProfileModify")
	public String myProfileModify(Principal principal,Model model) {
		Member member = memberService.findByEmail(principal.getName());

		model.addAttribute("profileFormDto",new ProfileFormDto());
		model.addAttribute("member",member);

		return "member/myProfileModify";
	}
	@PostMapping(value = "/myProfileModify")
	public String myProfileModify(@Valid ProfileFormDto profileFormDto, BindingResult bindingResult,Principal principal,@RequestParam("itemImgFile") MultipartFile itemImgFile,Model model) {

		if(bindingResult.hasErrors()) {
			return "member/myProfileModify";
		}

		try {
			memberService.updateProfile(profileFormDto,itemImgFile,principal.getName());
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage","프로필 수정 중 에러가 발생했습니다.");
			return "member/myProfileModify";
		}

		return "redirect:/myProfile";
	}

	@GetMapping(value = "/interests")
	public String selectInterests(Model model) {
		model.addAttribute("memberInterestsDto", new MemberInterestsDto());

		return "member/selectMemberInterests";
	}
	@PostMapping(value = "/interests")
	public String insertInterests(Principal principal, @Valid MemberInterestsDto memberInterestsDto) {
		MemberInterests memberInterest = memberInterestsService.loadMemberInterests(principal.getName());
		if(memberInterest != null) {
			Member member = memberService.findByEmail(principal.getName());
			memberInterestsService.updateMemberInterests(memberInterestsDto,member);
		} else {
			MemberInterests memberInterests = MemberInterests.createMemberInterests(memberInterestsDto);

			memberInterestsService.insertMemberInterests(principal.getName(),memberInterests);

		}


		return "redirect:/main";
	}

	@GetMapping(value = "/member/detail/{memberId}")
	public String memberDetail(@PathVariable("memberId") Long memberId,Model model,Principal principal) {

		Member member = memberService.findById(memberId);
		List<ProfilePostDto> profilePostDtoList = postService.findMyPost(member.getEmail());
		List<PostImgDto> postImgDtoList = postImageService.findMyImages(member.getMemberId());
		MemberInterests memberInterests = memberInterestsService.loadMemberInterests(member.getEmail());
		Member nowMember = memberService.findByEmail(principal.getName());

		model.addAttribute("nowMember", nowMember);
		model.addAttribute("myImages", postImgDtoList);
		model.addAttribute("posts", profilePostDtoList);
		model.addAttribute("member", member);
		model.addAttribute("memberInterests", memberInterests);

		return "member/userDetail";
	}
}
