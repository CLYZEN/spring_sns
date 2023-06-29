package com.sns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

	@GetMapping(value="login")
	public String login() {
		return "member/login";
	}
	
	@GetMapping(value="register")
	public String register() {
		return "member/register";
	}
	
	@GetMapping(value = "main")
	public String main() {
		return "html/main" ;
	}
	
	@GetMapping(value ="myProfile")
	public String myProfile() {
		return "member/myProfile";
	}
	
	@GetMapping(value = "myProfileModify")
	public String myProfileModify() {
		return "member/myProfileModify";
	}
}
