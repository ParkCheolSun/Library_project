package com.library.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.library.constant.Role;
import com.library.constant.WorkNumber;
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
		category.setCategory_name("자주하는 질문");
		categoryService.saveCategory(category);

		category = new Category();
		category.setCategory_id(13l);
		category.setCategory_name("건의사항");
		categoryService.saveCategory(category);

		category = new Category();
		category.setCategory_id(14l);
		category.setCategory_name("도서요청");
		categoryService.saveCategory(category);
		
		category = new Category();
		category.setCategory_id(15l);
		category.setCategory_name("예시게시판");
		categoryService.saveCategory(category);
	}

	@GetMapping("/board/list")
	public String getBoardListPage(Model model, @RequestParam(value = "kind", required = false) String kind,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size) throws Exception {

		if (searchKeyword == null) {

			try {
				model.addAttribute("resultMap", boardService.findAllBoard(page, size));
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
	@GetMapping("/board/faq/list")
	public String getBoardFAQListPage(Model model, @RequestParam(value = "kind", required = false) String kind,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size) throws Exception {

		if (searchKeyword == null) {

			try {
				model.addAttribute("resultMap", boardService.findAllFaq(page, size));
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
		return "board/faq/list";
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

	// 건의사항 리스트
	@GetMapping("/board/suggestion/list")
	public String getSuggestionBoardListPage(Model model, @RequestParam(value = "kind", required = false) String kind,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size) throws Exception {

		if (searchKeyword == null) {

			try {
				model.addAttribute("resultMap", boardService.findAllSuggestion(page, size));
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
		return "board/suggestion/list";
	}

	// 도서요청 리스트
	@GetMapping("/board/request/list")
	public String getRequestBoardListPage(Model model, @RequestParam(value = "kind", required = false) String kind,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size) throws Exception {

		if (searchKeyword == null) {

			try {
				model.addAttribute("resultMap", boardService.findAllRequest(page, size));
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
		return "board/request/list";
	}

	// 공지사항 작성
	@GetMapping("/board/noticeWrite")
	public String noticeBoardWrite(Model model, BoardRequestDto boardRequestDto) {
		return "board/noticeWrite";
	}

	// 건의사항 작성
	@GetMapping("/board/suggestion/write")
	public String suggestionBoardWrite(Model model, BoardRequestDto boardRequestDto) {
		return "board/suggestion/write";
	}
	
	// 도서요청 작성
		@GetMapping("/board/request/write")
		public String RequestBoardWrite(Model model, BoardRequestDto boardRequestDto) {
			return "board/request/write";
		}

	// 공지사항 삭제
	@PostMapping("/board/notice/delete")
	public String noticeBoardDelete(Model model, @RequestParam() Long[] deleteId, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		try {
			boardService.deleteAll(deleteId, myid, myRole, ip, WorkNumber.DELETE_NOTICE);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/notice";
	}

	// 건의사항 삭제
	@PostMapping("/board/suggestion/delete")
	public String suggestionBoardDelete(Model model, @RequestParam() Long[] deleteId, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		try {
			boardService.deleteAll(deleteId, myid, myRole, ip, WorkNumber.DELETE_SUGGESTION);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/suggestion/list";
	}
	
	// 도서요청 삭제
		@PostMapping("/board/request/delete")
		public String requestBoardDelete(Model model, @RequestParam() Long[] deleteId, HttpServletRequest request) throws Exception {
			HttpSession mySession = request.getSession();
			String myid = (String)mySession.getAttribute("id");
			Role myRole = (Role)mySession.getAttribute("Role");
			String ip = (String)mySession.getAttribute("ipaddress");
			try {
				boardService.deleteAll(deleteId, myid, myRole, ip, WorkNumber.DELETE_REQUEST);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}

			return "redirect:/board/request/list";
		}

	// 공지사항 작성 완료
	@PostMapping("/board/noticeWrite/action")
	public String noticeBoardWriteAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		try {
			Category category = categoryService.findCategory(10L);
			boardRequestDto.setCategory(category);
			if (!boardService.save(boardRequestDto, multiRequest, myid, myRole, ip, WorkNumber.CREATE_NOTICE)) {
				throw new Exception("#Exception boardWriteAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/notice";
	}

	// 건의사항 작성 완료
	@PostMapping("/board/suggestion/write/action")
	public String suggestionBoardWriteAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		try {
			Category category = categoryService.findCategory(13L);
			boardRequestDto.setCategory(category);
			if (!boardService.save(boardRequestDto, multiRequest, myid, myRole, ip, WorkNumber.CREATE_SUGGESTION)) {
				throw new Exception("#Exception boardWriteAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/suggestion/list";
	}
	
	// 도서요청 작성 완료
		@PostMapping("/board/request/write/action")
		public String requestBoardWriteAction(Model model, BoardRequestDto boardRequestDto,
				MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
			HttpSession mySession = request.getSession();
			String myid = (String)mySession.getAttribute("id");
			Role myRole = (Role)mySession.getAttribute("Role");
			String ip = (String)mySession.getAttribute("ipaddress");
			try {
				Category category = categoryService.findCategory(14L);
				boardRequestDto.setCategory(category);
				if (!boardService.save(boardRequestDto, multiRequest, myid, myRole, ip, WorkNumber.CREATE_REQUEST)) {
					throw new Exception("#Exception boardWriteAction!");
				}
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}

			return "redirect:/board/request/list";
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

	// 건의사항 세부사항 보기
	@GetMapping("/board/suggestion/view")
	public String suggestionBoardDetailView(Model model, BoardRequestDto boardRequestDto) throws Exception {
		try {
			if (boardRequestDto.getId() != null) {
				model.addAttribute("test", boardService.findById(boardRequestDto.getId()));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "board/suggestion/view";
	}
	
	// 건의사항 세부사항 보기
		@GetMapping("/board/request/view")
		public String requestBoardDetailView(Model model, BoardRequestDto boardRequestDto) throws Exception {
			try {
				if (boardRequestDto.getId() != null) {
					model.addAttribute("test", boardService.findById(boardRequestDto.getId()));
				}
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}

			return "board/request/view";
		}

	// 공지사항 세부사항 수정
	@PostMapping("/board/noticeDetailView/action")
	public String noticeBoardDetailViewAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		try {
			boolean result = boardService.updateBoard(boardRequestDto, multiRequest, myid, myRole, ip, WorkNumber.UPDATE_NOTICE);

			if (!result) {
				throw new Exception("#Exception boardViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/notice";
	}

	// 건의사항 세부사항 수정
	@PostMapping("/board/suggestion/view/action")
	public String suggestionBoardDetailViewAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		try {
			boolean result = boardService.updateBoard(boardRequestDto, multiRequest, myid, myRole, ip, WorkNumber.UPDATE_SUGGESTION);

			if (!result) {
				throw new Exception("#Exception boardViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/suggestion/list";
	}
	
	// 도서요청 세부사항 수정
		@PostMapping("/board/request/view/action")
		public String requestBoardDetailViewAction(Model model, BoardRequestDto boardRequestDto,
				MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
			HttpSession mySession = request.getSession();
			String myid = (String)mySession.getAttribute("id");
			Role myRole = (Role)mySession.getAttribute("Role");
			String ip = (String)mySession.getAttribute("ipaddress");
			try {
				boolean result = boardService.updateBoard(boardRequestDto, multiRequest, myid, myRole, ip, WorkNumber.UPDATE_REQUEST);

				if (!result) {
					throw new Exception("#Exception boardViewAction!");
				}
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}

			return "redirect:/board/request/list";
		}

	// 공지사항 세부사항 삭제
	@PostMapping("/board/noticeDetailView/delete")
	public String noticeBoardDetailViewDelete(Model model, @RequestParam() Long id, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		try {
			boardService.deleteById(id, myid, myRole, ip, WorkNumber.DELETE_NOTICE);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/notice";
	}

	// 건의사항 세부사항 삭제
	@PostMapping("/board/suggestion/view/delete")
	public String suggestionBoardDetailViewDelete(Model model, @RequestParam() Long id, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		try {
			boardService.deleteById(id, myid, myRole, ip, WorkNumber.DELETE_SUGGESTION);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/suggestion/list";
	}
	
	// 건의사항 세부사항 삭제
		@PostMapping("/board/request/view/delete")
		public String requestBoardDetailViewDelete(Model model, @RequestParam() Long id, HttpServletRequest request) throws Exception {
			HttpSession mySession = request.getSession();
			String myid = (String)mySession.getAttribute("id");
			Role myRole = (Role)mySession.getAttribute("Role");
			String ip = (String)mySession.getAttribute("ipaddress");
			try {
				boardService.deleteById(id, myid, myRole, ip, WorkNumber.DELETE_REQUEST);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}

			return "redirect:/board/request/list";
		}

	@GetMapping("/board/write")
	public String getBoardWritePage(Model model, BoardRequestDto boardRequestDto) {

		return "board/write";
	}

	// FAQ 작성
	@GetMapping("/board/faq/write")
	public String BoardFAQWrite(Model model, BoardRequestDto boardRequestDto) {
		return "board/faq/write";
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
	@GetMapping("/board/faq/view")
	public String getBoardFAQViewPage(Model model, BoardRequestDto boardRequestDto) throws Exception {
		System.out.println(boardRequestDto.getId());
		try {
			if (boardRequestDto.getId() != null) {
				model.addAttribute("test", boardService.findById(boardRequestDto.getId()));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "board/faq/view";
	}

	@PostMapping("/board/write/action")
	public String boardWriteAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		try {
			Category category = categoryService.findCategory(11L);
			boardRequestDto.setCategory(category);
			if (!boardService.save(boardRequestDto, multiRequest, myid, myRole, ip, WorkNumber.CREATE_FAQ)) {
				throw new Exception("#Exception boardWriteAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/list";
	}

	// FAQ 작성 완료
	@PostMapping("/board/faq/write/action")
	public String boardFAQWriteAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		try {
			Category category = categoryService.findCategory(12L);
			boardRequestDto.setCategory(category);
			if (!boardService.save(boardRequestDto, multiRequest, myid, myRole, ip, WorkNumber.CREATE_FAQ)) {
				throw new Exception("#Exception boardWriteAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/faq/list";
	}

	@PostMapping("/board/view/action")
	public String boardViewAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		try {
			boolean result = boardService.updateBoard(boardRequestDto, multiRequest, myid, myRole, ip, WorkNumber.UPDATE_FAQ);

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
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		try {
			boolean result = boardService.updateBoard(boardRequestDto, multiRequest, myid, myRole, ip, WorkNumber.UPDATE_FAQ);

			if (!result) {
				throw new Exception("#Exception boardViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/faq/list";
	}

	@PostMapping("/board/view/delete")
	public String boardViewDeleteAction(Model model, @RequestParam() Long id, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		try {
			boardService.deleteById(id, myid, myRole, ip, WorkNumber.DELETE_FAQ);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/list";
	}

	// FAQ 세부사항 삭제
	@PostMapping("/faq/view/delete")
	public String boardFAQViewDeleteAction(Model model, @RequestParam() Long id, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		try {
			boardService.deleteById(id, myid, myRole, ip, WorkNumber.DELETE_FAQ);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/faq/list";
	}

	@PostMapping("/board/delete")
	public String boardDeleteAction(Model model, @RequestParam() Long[] deleteId, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		try {
			boardService.deleteAll(deleteId, myid, myRole, ip, WorkNumber.DELETE_FAQ);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/list";
	}

	// FAQ 삭제
	@PostMapping("/faq/delete")
	public String boardFAQDeleteAction(Model model, @RequestParam() Long[] deleteId, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		try {
			boardService.deleteAll(deleteId, myid, myRole, ip, WorkNumber.DELETE_FAQ);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/faq/list";
	}

}
