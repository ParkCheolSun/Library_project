package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.library.dto.BoardRequestDto;
import com.library.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardService boardService;

	@GetMapping("/board/list")
	public String getBoardListPage(Model model,@RequestParam(value="searchKeyword", required = false)String searchKeyword, 
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size) throws Exception {
		
		if(searchKeyword == null) {

		try {
			model.addAttribute("resultMap", boardService.findAll(page, size));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		}else {
			model.addAttribute("resultMap", boardService.findByTitleContaining(page, size, searchKeyword));
		}
		System.out.println(boardService.findAll(page, size));
		return "board/list";
	}

	@GetMapping("/board/write")
	public String getBoardWritePage(Model model, BoardRequestDto boardRequestDto) {
		
		return "board/write";
	}

	@GetMapping("/board/view")
	public String getBoardViewPage(Model model, BoardRequestDto boardRequestDto) throws Exception {
		System.out.println(boardRequestDto.getId());
		try {
			if (boardRequestDto.getId() != null) {
				model.addAttribute("test", boardService.findById(boardRequestDto.getId()));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "board/view";
	}

	@PostMapping("/board/write/action")
	public String boardWriteAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest) throws Exception {

		try {
			if (!boardService.save(boardRequestDto, multiRequest)) {
				throw new Exception("#Exception boardWriteAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/list";
	}

	@PostMapping("/board/view/action")
	public String boardViewAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest) throws Exception {

		try {
			boolean result = boardService.updateBoard(boardRequestDto, multiRequest);

			if (!result) {
				throw new Exception("#Exception boardViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/list";
	}

	@PostMapping("/board/view/delete")
	public String boardViewDeleteAction(Model model, @RequestParam() Long id) throws Exception {
		try {
			boardService.deleteById(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/list";
	}

	@PostMapping("/board/delete")
	public String boardDeleteAction(Model model, @RequestParam() Long[] deleteId) throws Exception {

		try {
			boardService.deleteAll(deleteId);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/list";
	}

}
