package com.library.controller;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.library.dto.MemberDto;
import com.library.entity.Member;
import com.library.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/Login")
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;

	@GetMapping(value = "/SignUp")
	public String memberForm(Model model) {
		model.addAttribute("memberDto", new MemberDto());
		return "member/SignUpForm";
	}
	
	@PostMapping(value = "/SignIn")
	public String memberSignIn(Model model) {
		model.addAttribute("memberDto", new MemberDto());
		return "member/SignUpForm";
	}

	@PostMapping(value = "/Save")
	public String newMember(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {
		System.out.println(memberDto.toString());
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.toString());
			return "member/SignUpForm";
		}
		try {
			System.out.println("정상구문!!!!");
			Member member = Member.createMember(memberDto, passwordEncoder);
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/SignUpForm";
		}
		return "redirect:/";
	}
}
