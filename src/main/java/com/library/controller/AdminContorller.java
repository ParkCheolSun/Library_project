package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminContorller {

	@GetMapping("/admin/accounts")
	public String adminAccountsPage(Model model) {
		return "admin/accounts";
	}
}
