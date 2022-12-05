package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.library.dto.MemberResponseDto;
import com.library.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminContorller {
	private final MemberService memberService;

	@GetMapping(value = "/accounts")
	public String adminAccountsPage(Model model) {
		model.addAttribute("memberResponseDto", memberService.findAll());
		return "admin/accounts";
	}
	
	@PutMapping(value = "/memberUpdate")
	public String adminUpdate(MemberResponseDto memberResDto, Model model) {
		memberService.updateMember_admin(memberResDto);
		return "redirect:/admin/accounts";
	}
	
	@PostMapping(value = "/memberSave")
	public String adminSave(MemberResponseDto memberResDto, Model model) {
		memberService.saveMember(memberResDto);
		return "redirect:/admin/accounts";
	}
}
