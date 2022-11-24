package com.library.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.library.entity.Member;
import com.library.service.MemberListService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	// 기본형
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MemberListService memberListService;

	// 모든 회원 조회
	@GetMapping("/memberList/RoleSelect")
	public String list(Model model) {
		List<Member> members = memberListService.findAll();
		model.addAttribute("member", members);

		return "admin/adminUserList";
	}

	@GetMapping("/memberList/RoleSelect")
	public String list(Model1 model) {
		List<Member> members = memberListService.findById(id);
	}

}