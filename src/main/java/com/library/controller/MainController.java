package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//시큐리티 하기전이라 무시중
//@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class MainController {
	@GetMapping(value = "/")
	public String main() {
		return "main";
	}
	
	
}
