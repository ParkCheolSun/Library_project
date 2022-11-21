package com.library.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.support.SessionStatus;

@Controller
//시큐리티 하기전이라 무시중
//@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class MainController {
	@GetMapping(value = "/")
	public String main(@SessionAttribute(name = "mes", required = false) String mes, HttpServletRequest request, Model model) {
		model.addAttribute("meslog", mes);
		HttpSession session = request.getSession();
		session.setAttribute("mes", "");
		return "main";
	}
	
	
}
