package com.sns.controller;

import java.security.Principal;

import com.sns.dto.MemberInterestsDto;
import com.sns.repository.MemberInterestsRepository;
import com.sns.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {
	
	private final MemberInterestsRepository memberInterestsRepository;
	private final MemberRepository memberRepository;
	@GetMapping(value = "/")
	public String index(Principal principal, Model model) {
	    if (principal != null && principal.getName() != null) {

			// 관심사를 아직 선택하지 않았을 때
			if (memberInterestsRepository.findByMember(memberRepository.findByEmail(principal.getName())) == null) {
				model.addAttribute("memberInterestsDto",new MemberInterestsDto());
				return "member/selectMemberInterests";
			}

	        return "redirect:/main";
	    } else {
	        return "index";
	    }
		//return "index";
	}
/*
	@GetMapping(value = "/")
	public String index(Principal principal, Model model) {
		if (principal != null && principal.getName() != null) {
			// 관심사를 아직 선택하지 않았을 때
			if (memberInterestsRepository.findByMember(memberRepository.findByEmail(principal.getName())) == null) {
				model.addAttribute("memberInterestsDto", new MemberInterestsDto());
				return "member/selectMemberInterests";
			} else {
				return "redirect:/main";
			}
		} else {
			return "index";
		}
	}
	*/
	@GetMapping(value = "/contact")
	public String contact() {
		return "html/contact";
	}
}
