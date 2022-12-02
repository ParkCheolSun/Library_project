package com.library.controller;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.library.dto.BoardRequestDto;
import com.library.entity.Category;
import com.library.service.BoardService;
import com.library.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardService boardService;
	private final CategoryService categoryService;

	@PostConstruct
	private void createCategory() {
		// 카테고리 DB 체크
		boolean check = categoryService.findAll();
		if (check)
			return;
		Category category = new Category();
		category.setCategory_id(10l);
		category.setCategory_name("공지사항");
		categoryService.saveCategory(category);

		category = new Category();
		category.setCategory_id(11l);
		category.setCategory_name("자유게시판");
		categoryService.saveCategory(category);

		category = new Category();
		category.setCategory_id(12l);
		category.setCategory_name("FAQ");
		categoryService.saveCategory(category);
	}

	@GetMapping("/board/list")
	public String getBoardListPage(Model model, @RequestParam(value = "kind", required = false) String kind,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size) throws Exception {

		if (searchKeyword == null) {

			try {
				model.addAttribute("resultMap", boardService.findAll(page, size));
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}

		} else {
			String test2 = "제목";

			if (test2.equals(kind) == true) {
				model.addAttribute("resultMap", boardService.findByTitleContaining(page, size, searchKeyword));
			} else {

				model.addAttribute("resultMap", boardService.findByContentContaining(page, size, searchKeyword));
			}
		}
		System.out.println(boardService.findAll(page, size));
		return "board/list";
	}
	
	// FAQ 리스트
		@GetMapping("/faq/list")
		public String getBoardFAQListPage(Model model, @RequestParam(value = "kind", required = false) String kind,
				@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
				@RequestParam(required = false, defaultValue = "0") Integer page,
				@RequestParam(required = false, defaultValue = "10") Integer size) throws Exception {

			if (searchKeyword == null) {

				try {
					model.addAttribute("resultMap", boardService.findAll(page, size));
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}

			} else {
				String findTitle = "제목";

				if (findTitle.equals(kind) == true) {
					model.addAttribute("resultMap", boardService.findByTitleContaining(page, size, searchKeyword));
				} else {

					model.addAttribute("resultMap", boardService.findByContentContaining(page, size, searchKeyword));
				}
			}
			System.out.println(boardService.findAll(page, size));
			return "faq/list";
		}

	// 공지사항 리스트
	@GetMapping("/board/notice")
	public String noticeBoardList(Model model, @RequestParam(value = "kind", required = false) String kind,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size) throws Exception {

		if (searchKeyword == null) {

			try {
				model.addAttribute("resultMap", boardService.findAllNotice(page, size));
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}

		} else {
			String kindTitle = "제목";

			if (kindTitle.equals(kind) == true) {
				model.addAttribute("resultMap", boardService.findByTitleContainingNotice(page, size, searchKeyword));
			} else {

				model.addAttribute("resultMap", boardService.findByContentContainingNotice(page, size, searchKeyword));
			}
		}

		return "/board/noticeListView";
	}

	// 공지사항 작성
	@GetMapping("/board/noticeWrite")
	public String noticeBoardWrite(Model model, BoardRequestDto boardRequestDto) {
		return "board/noticeWrite";
	}

	// 공지사항 삭제
	@PostMapping("/board/notice/delete")
	public String noticeBoardDelete(Model model, @RequestParam() Long[] deleteId) throws Exception {

		try {
			boardService.deleteAll(deleteId);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/notice";
	}

	// 공지사항 작성 완료
	@PostMapping("/board/noticeWrite/action")
	public String noticeBoardWriteAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest) throws Exception {
		try {
			Category category = categoryService.findCategory(10L);
			boardRequestDto.setCategory(category);
			if (!boardService.save(boardRequestDto, multiRequest)) {
				throw new Exception("#Exception boardWriteAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/notice";
	}

	// 공지사항 세부사항 보기
	@GetMapping("/board/noticeDetailView")
	public String noticeBoardDetailView(Model model, BoardRequestDto boardRequestDto) throws Exception {
		try {
			if (boardRequestDto.getId() != null) {
				model.addAttribute("test", boardService.findById(boardRequestDto.getId()));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "board/noticeDetailView";
	}

	// 공지사항 세부사항 수정
	@PostMapping("/board/noticeDetailView/action")
	public String noticeBoardDetailViewAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest) throws Exception {

		try {
			boolean result = boardService.updateBoard(boardRequestDto, multiRequest);

			if (!result) {
				throw new Exception("#Exception boardViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/notice";
	}

	// 공지사항 세부사항 삭제
	@PostMapping("/board/noticeDetailView/delete")
	public String noticeBoardDetailViewDelete(Model model, @RequestParam() Long id) throws Exception {
		try {
			boardService.deleteById(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/notice";
	}

	@GetMapping("/board/write")
	public String getBoardWritePage(Model model, BoardRequestDto boardRequestDto) {

		return "board/write";
	}
	
	// FAQ 작성
		@GetMapping("/faq/write")
		public String BoardFAQWrite(Model model, BoardRequestDto boardRequestDto) {
			return "faq/write";
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
	// FAQ 상세보기
	@GetMapping("/faq/view")
	public String getBoardFAQViewPage(Model model, BoardRequestDto boardRequestDto) throws Exception {
		System.out.println(boardRequestDto.getId());
		try {
			if (boardRequestDto.getId() != null) {
				model.addAttribute("test", boardService.findById(boardRequestDto.getId()));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "faq/view";
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
	// FAQ 작성 완료
	@PostMapping("/faq/write/action")
	public String boardFAQWriteAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest) throws Exception {

		try {
			Category category = categoryService.findCategory(12L);
			boardRequestDto.setCategory(category);
			if (!boardService.save(boardRequestDto, multiRequest)) {
				throw new Exception("#Exception boardWriteAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/faq/list";
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
	// FAQ 수정 완료
	@PostMapping("/faq/view/action")
	public String boardFAQViewAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest) throws Exception {

		try {
			boolean result = boardService.updateBoard(boardRequestDto, multiRequest);

			if (!result) {
				throw new Exception("#Exception boardViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/faq/list";
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
	// FAQ 세부사항 삭제
	@PostMapping("/faq/view/delete")
	public String boardFAQViewDeleteAction(Model model, @RequestParam() Long id) throws Exception {
		try {
			boardService.deleteById(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/faq/list";
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
	// FAQ 삭제
	@PostMapping("/faq/delete")
	public String boardFAQDeleteAction(Model model, @RequestParam() Long[] deleteId) throws Exception {

		try {
			boardService.deleteAll(deleteId);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/faq/list";
	}

}
