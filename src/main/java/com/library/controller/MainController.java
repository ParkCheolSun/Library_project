package com.library.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.library.dto.BookDto;
import com.library.service.MainService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final MainService mainService;
	private final List<BookDto> bookDtoList;
	
	@GetMapping(value = "/")
	public String main(@SessionAttribute(name = "mes", required = false) String mes, HttpServletRequest request, Model model) {
		model.addAttribute("meslog", mes);
		HttpSession session = request.getSession();
		session.setAttribute("mes", "");
		
		// api book data
		bookDtoList.clear();
		bookDtoList.addAll(mainService.popularityBook());
		System.out.println("bookList : " + bookDtoList);
		model.addAttribute("bookDtoList", bookDtoList);
		return "main";
	}
	
	
}
