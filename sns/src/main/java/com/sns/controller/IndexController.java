package com.sns.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
public class IndexController {
	
	
	@GetMapping(value = "/")
	public String index(Principal principal) {
	    if (principal != null && principal.getName() != null) {
	        return "redirect:/main";
	    } else {
	        return "index";
	    }
		//return "index";
	}
	
	
	@GetMapping(value = "/contact")
	public String contact() {
		return "html/contact";
	}
}
