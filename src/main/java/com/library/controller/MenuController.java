package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/header")
public class MenuController {

	// 도서관 안내
	@GetMapping(value = "/guide")
	public String guide(Model model) {
		return "contents/greetings";
	}

	@GetMapping(value = "/greetings")
	public String greetings(Model model) {
		return "contents/greetings";
	}

	@GetMapping(value = "/a2")
	public String a2(Model model) {
		System.out.println("도서관 소개");
		return "redirect:/";
	}

	@GetMapping(value = "/a3")
	public String a3(Model model) {
		System.out.println("찾아오시는 길");
		return "redirect:/";
	}

	// 문화 프로그램
	@GetMapping(value = "/test2")
	public String test2(Model model) {
		System.out.println("문화 프로그램");
		return "redirect:/header/b1";
	}

	@GetMapping(value = "/b1")
	public String b1(Model model) {
		System.out.println("프로그램 신청");
		return "redirect:/";
	}

	// 알람마당
	@GetMapping(value = "/test3")
	public String test3(Model model) {
		System.out.println("알림마당");
		return "redirect:/header/c1";
	}

	@GetMapping(value = "/notice")
	public String c1(Model model) {
		System.out.println("공지사항");
		return "redirect:/board/notice";
	}

	// 열린마당
	@GetMapping(value = "/test4")
	public String test4(Model model) {
		System.out.println("열린마당");
		return "redirect:/board/list";
	}

	@GetMapping(value = "/board")
	public String d1(Model model) {
		System.out.println("자유게시판");
		return "redirect:/board/list";
	}

	@GetMapping(value = "/faq")
	public String d2(Model model) {
		System.out.println("자주하는 질문");
		return "redirect:/board/faq/list";
	}

	@GetMapping(value = "/suggestion")
	public String d3(Model model) {
		System.out.println("건의사항");
		return "redirect:/board/suggestion/list";
	}

	@GetMapping(value = "/request")
	public String d4(Model model) {
		System.out.println("도서신청");
		return "redirect:/board/request/list";
	}
	
	// 작은 도서관
	@GetMapping(value = "/smallLibrary")
	public String e1(Model model) {
		System.out.println("작은도서관 소개");
		return "redirect:/small/small";
	}

	@GetMapping(value = "/smallList")
	public String e2(Model model) {
		System.out.println("작은도서관 소식");
		return "redirect:/small/smallList";
	}
}