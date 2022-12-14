package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/header")
public class MenuController {

	// 도서관 안내, 인사말(Header, Header - sub)
	@GetMapping(value = "/greetings")
	public String guide(Model model) {
		return "contents/greetings";
	}

	// 도서관 안내 - 도서관 소개(Header - sub)
	@GetMapping(value = "/intro")
	public String intro(Model model) {
		return "contents/intro";
	}

	// 알림마당(Header)
	@GetMapping(value = "/notice")
	public String notice(Model model) {
		return "redirect:/board/notice";
	}

	// 열린마당, 자유게시판(Header, Header - sub)
	@GetMapping(value = "/boardList")
	public String boardList_h(Model model) {
		return "redirect:/board/list";
	}

	// 열린마당 - FAQ(Header - sub)
	@GetMapping(value = "/faq")
	public String FAQ(Model model) {
		return "redirect:/board/faq/list";
	}

	// 열린마당 - 건의사항(Header - sub)
	@GetMapping(value = "/suggestion")
	public String suggestion(Model model) {
		return "redirect:/board/suggestion/list";
	}

	// 열린마당 - 도서신청(Header - sub)
	@GetMapping(value = "/request")
	public String requestList(Model model) {
		return "redirect:/board/request/list";
	}
	
	// 작은 도서관(Header, Header - sub)
	@GetMapping(value = "/smallLibrary")
	public String e1(Model model) {
		return "redirect:/small/small";
	}

	// 작은 도서관 - 작은도서관 소식(Header - sub)
	@GetMapping(value = "/smallList")
	public String e2(Model model) {
		return "redirect:/small/smallList";
	}
}