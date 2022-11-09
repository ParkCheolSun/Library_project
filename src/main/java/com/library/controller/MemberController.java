package com.library.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	// ajax를 통한 ID체크
	@ResponseBody
	@PostMapping(value = "/IdCheck")
	public HashMap<String, String> idCheck(@RequestParam("id") String id) {
		boolean check = memberService.findById(id);
		 HashMap<String, String> map = new HashMap<String, String>();
		if(check) {
			map.put("answer", "Fail");
		} else {
			map.put("answer", "Success");
		}
		return map;
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
