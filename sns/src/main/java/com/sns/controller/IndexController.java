package com.sns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping(value = "/")
	public String index() {
		return "index";
	}
	
	@GetMapping(value = "contact")
	public String contact() {
		return "html/contact";
	}
}
