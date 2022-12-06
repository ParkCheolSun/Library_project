package com.library.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.dto.BookResponseDto;
import com.library.service.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BookController {
	private final BookService bookService;
	
	@GetMapping(value = "/search")
	public String memberForm(@RequestParam("keyword") String keyword ,Model model) {
		List<BookResponseDto> bookResDtoList = bookService.searchBook(keyword);
		model.addAttribute("bookResDtoList", bookResDtoList);
		return "book/search";
	}
}
