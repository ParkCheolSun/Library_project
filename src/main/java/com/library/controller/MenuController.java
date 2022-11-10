package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/header")
public class MenuController {
	
	@GetMapping(value = "/test1")
	public String test1(Model model){
	System.out.println("도서관 안내 누름");
	return "redirect:/";
	}
}
