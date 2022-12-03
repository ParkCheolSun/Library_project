package com.library.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.library.dto.MemberDto;
import com.library.entity.Member;
import com.library.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminContorller {
	private final MemberService memberService;

	@GetMapping("/admin/accounts")
	public String adminAccountsPage(Model model) {
		model.addAttribute("memberResponseDto", memberService.findAll());
		return "admin/accounts";
	}
}
