package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.library.dto.MemberDto;
import com.library.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/Login")
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@GetMapping(value = "/SignUp")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberDto());
		return "member/SignUpForm";
	}
}
