package com.library.controller;

import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.library.constant.Role;
import com.library.constant.WorkNumber;
import com.library.dto.BoardReplyRequestDto;
import com.library.dto.BoardRequestDto;
import com.library.entity.Category;
import com.library.service.BoardService;
import com.library.service.CategoryService;
import com.library.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

	private final MemberService memberService;
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
		category.setCategory_name("작은도서관 소식");
		categoryService.saveCategory(category);

	}

	// 발표용 게시판 생성
	@PostConstruct
	private void createTestBoard() {
		BoardRequestDto boardRequestDto;
		Category category = new Category();
		// 공지사항
		category.setCategory_id(10l);
		for (int i = 1; i < 13; i++) {
			boardRequestDto = new BoardRequestDto();
			boardRequestDto.setRegisterId("manager");
			boardRequestDto.setReadCnt(0);
			boardRequestDto.setTitle("공지사항 입니다. [ " + i + " ]");
			boardRequestDto.setContent("공지사항 내용입니다. [ " + i + " ]");
			boardRequestDto.setCategory(category);
			try {
				boardService.save(boardRequestDto, null, "manager", Role.MANAGER, "Test IP " + i,
						WorkNumber.CREATE_NOTICE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 자유게시판
		category.setCategory_id(11l);
		for (int i = 1; i < 13; i++) {
			boardRequestDto = new BoardRequestDto();
			boardRequestDto.setRegisterId("User" + i);
			boardRequestDto.setReadCnt(0);
			boardRequestDto.setTitle("자유게시판 입니다. [ " + i + " ]");
			boardRequestDto.setContent("자유게시판 내용입니다. [ " + i + " ]");
			boardRequestDto.setCategory(category);
			try {
				boardService.save(boardRequestDto, null, "manager", Role.MANAGER, "Test IP " + i,
						WorkNumber.CREATE_FREE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 자주하는질문(FAQ)
		category.setCategory_id(12l);
		for (int i = 1; i < 13; i++) {
			boardRequestDto = new BoardRequestDto();
			boardRequestDto.setRegisterId("manager");
			boardRequestDto.setReadCnt(0);
			boardRequestDto.setTitle("자주하는질문 게시판 입니다. [ " + i + " ]");
			boardRequestDto.setContent("자주하는질문 게시판 내용입니다. [ " + i + " ]");
			boardRequestDto.setCategory(category);
			try {
				boardService.save(boardRequestDto, null, "manager", Role.MANAGER, "Test IP " + i,
						WorkNumber.CREATE_FAQ);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 건의사항
		category.setCategory_id(13l);
		for (int i = 1; i < 13; i++) {
			boardRequestDto = new BoardRequestDto();
			boardRequestDto.setRegisterId("manager");
			boardRequestDto.setReadCnt(0);
			boardRequestDto.setTitle("건의사항 게시판 입니다. [ " + i + " ]");
			boardRequestDto.setContent("건의사항 게시판 내용입니다. [ " + i + " ]");
			boardRequestDto.setCategory(category);
			try {
				boardService.save(boardRequestDto, null, "manager", Role.MANAGER, "Test IP " + i,
						WorkNumber.CREATE_SUGGESTION);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 도서요청
		category.setCategory_id(14l);
		for (int i = 1; i < 13; i++) {
			boardRequestDto = new BoardRequestDto();
			boardRequestDto.setRegisterId("manager");
			boardRequestDto.setReadCnt(0);
			boardRequestDto.setTitle("도서요청 게시판 입니다. [ " + i + " ]");
			boardRequestDto.setContent("도서요청 게시판 내용입니다. [ " + i + " ]");
			boardRequestDto.setCategory(category);
			try {
				boardService.save(boardRequestDto, null, "User" + i, Role.MANAGER, "Test IP " + i,
						WorkNumber.CREATE_REQUEST);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 작은도서관
		category.setCategory_id(15l);
		for (int i = 1; i < 13; i++) {
			boardRequestDto = new BoardRequestDto();
			boardRequestDto.setRegisterId("manager");
			boardRequestDto.setReadCnt(0);
			boardRequestDto.setTitle("작은도서관 게시판 입니다. [ " + i + " ]");
			boardRequestDto.setContent("작은도서관 게시판 내용입니다. [ " + i + " ]");
			boardRequestDto.setCategory(category);
			try {
				boardService.save(boardRequestDto, null, "manager", Role.MANAGER, "Test IP " + i,
						WorkNumber.CREATE_SMALLLIBRARY);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*----------------------------------------------자유게시판---------------------------------------------*/

	// 자유게시판 작성
	@GetMapping("/board/write")
	public String getBoardWritePage(Model model, BoardRequestDto boardRequestDto) {

		return "board/write";
	}

	// 자유게시판 리스트
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
				model.addAttribute("resultMap", boardService.findByTitleContainingBoard(page, size, searchKeyword));
			} else {

				model.addAttribute("resultMap", boardService.findByContentContainingBoard(page, size, searchKeyword));
			}
		}
		System.out.println(boardService.findAll(page, size));
		return "board/list";
	}

	// 자유게시판 세부사항 보기
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

	// 자유게시판 세부사항 작성
	@PostMapping("/board/write/action")
	public String boardWriteAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			Category category = categoryService.findCategory(11L);
			boardRequestDto.setCategory(category);
			if (!boardService.save(boardRequestDto, multiRequest, myid, myRole, ip, WorkNumber.CREATE_FREE)) {
				throw new Exception("#Exception boardWriteAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/list";
	}

	// 자유게시판 세부사항 수정
	@PostMapping("/board/view/action")
	public String boardViewAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boolean result = boardService.updateBoard(boardRequestDto, multiRequest, myid, myRole, ip,
					WorkNumber.UPDATE_FREE);

			if (!result) {
				throw new Exception("#Exception boardViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/list";
	}

	// 자유게시판 세부사항 삭제
	@PostMapping("/board/view/delete")
	public String boardViewDeleteAction(Model model, @RequestParam() Long id, HttpServletRequest request)
			throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boardService.deleteById(id, myid, myRole, ip, WorkNumber.DELETE_FREE);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/list";
	}

	// 자유게시판 삭제(관리자, 매니저)
	@PostMapping("/board/delete")
	public String boardDeleteAction(Model model, @RequestParam() Long[] deleteId, HttpServletRequest request)
			throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boardService.deleteAll(deleteId, myid, myRole, ip, WorkNumber.DELETE_FREE);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/list";
	}

	/*--------------------------------------------------------------------------------------------------*/

	/*--------------------------------------------공지사항게시판--------------------------------------------*/

	// 공지사항 작성
	@GetMapping("/board/noticeWrite")
	public String noticeBoardWrite(Model model, BoardRequestDto boardRequestDto) {
		return "board/noticeWrite";
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

	// 공지사항 저장
	@PostMapping("/board/noticeWrite/action")
	public String noticeBoardWriteAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
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
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boolean result = boardService.updateBoard(boardRequestDto, multiRequest, myid, myRole, ip,
					WorkNumber.UPDATE_NOTICE);

			if (!result) {
				throw new Exception("#Exception boardViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/notice";
	}

	// 공지사항 삭제(복수개)
	@PostMapping("/board/notice/delete")
	public String noticeBoardDelete(Model model, @RequestParam() Long[] deleteId, HttpServletRequest request)
			throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boardService.deleteAll(deleteId, myid, myRole, ip, WorkNumber.DELETE_NOTICE);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/notice";
	}

	// 공지사항 삭제(단일)
	@PostMapping("/board/noticeDetailView/delete")
	public String noticeBoardDetailViewDelete(Model model, @RequestParam() Long id, HttpServletRequest request)
			throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boardService.deleteById(id, myid, myRole, ip, WorkNumber.DELETE_NOTICE);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/notice";
	}

	/*--------------------------------------------------------------------------------------------------*/

	/*--------------------------------------------건의사항게시판--------------------------------------------*/

	// 건의사항 작성
	@GetMapping("/board/suggestion/write")
	public String suggestionBoardWrite(Model model, BoardRequestDto boardRequestDto) {
		return "board/suggestion/write";
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
				model.addAttribute("resultMap",
						boardService.findByTitleContainingSuggestion(page, size, searchKeyword));
			} else {

				model.addAttribute("resultMap",
						boardService.findByContentContainingSuggestion(page, size, searchKeyword));
			}
		}
		System.out.println(boardService.findAll(page, size));
		return "board/suggestion/list";
	}

	// 건의사항 저장
	@PostMapping("/board/suggestion/write/action")
	public String suggestionBoardWriteAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
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

	// 건의사항 세부사항 수정
	@PostMapping("/board/suggestion/view/action")
	public String suggestionBoardDetailViewAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boolean result = boardService.updateBoard(boardRequestDto, multiRequest, myid, myRole, ip,
					WorkNumber.UPDATE_SUGGESTION);

			if (!result) {
				throw new Exception("#Exception boardViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/suggestion/list";
	}

	// 건의사항 삭제(복수)
	@PostMapping("/board/suggestion/list/delete")
	public String suggestionBoardDelete(Model model, @RequestParam() Long[] deleteId, HttpServletRequest request)
			throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boardService.deleteAll(deleteId, myid, myRole, ip, WorkNumber.DELETE_SUGGESTION);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/suggestion/list";
	}

	// 건의사항 삭제(단일)
	@PostMapping("/board/suggestion/view/delete")
	public String suggestionBoardDetailViewDelete(Model model, @RequestParam() Long id, HttpServletRequest request)
			throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boardService.deleteById(id, myid, myRole, ip, WorkNumber.DELETE_SUGGESTION);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/suggestion/list";
	}

	/*--------------------------------------------------------------------------------------------------*/

	/*----------------------------------------------FAQ게시판---------------------------------------------*/

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
				model.addAttribute("resultMap", boardService.findByTitleContainingFaq(page, size, searchKeyword));
			} else {

				model.addAttribute("resultMap", boardService.findByContentContainingFaq(page, size, searchKeyword));
			}
		}
		System.out.println(boardService.findAll(page, size));
		return "board/faq/list";
	}

	// FAQ 작성
	@GetMapping("/board/faq/write")
	public String BoardFAQWrite(Model model, BoardRequestDto boardRequestDto) {
		return "board/faq/write";
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

	// FAQ 저장
	@PostMapping("/board/faq/write/action")
	public String boardFAQWriteAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
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

	// FAQ 수정
	@PostMapping("/board/faq/view/action")
	public String boardFAQViewAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boolean result = boardService.updateBoard(boardRequestDto, multiRequest, myid, myRole, ip,
					WorkNumber.UPDATE_FAQ);

			if (!result) {
				throw new Exception("#Exception boardViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/faq/list";
	}

	// FAQ 삭제(단일)
	@PostMapping("/board/faq/view/delete")
	public String boardFAQViewDeleteAction(Model model, @RequestParam() Long id, HttpServletRequest request)
			throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boardService.deleteById(id, myid, myRole, ip, WorkNumber.DELETE_FAQ);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/faq/list";
	}

	// FAQ 삭제(복수)
	@PostMapping("/board/faq/list/delete")
	public String boardFAQDeleteAction(Model model, @RequestParam() Long[] deleteId, HttpServletRequest request)
			throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boardService.deleteAll(deleteId, myid, myRole, ip, WorkNumber.DELETE_FAQ);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/faq/list";
	}

	/*--------------------------------------------------------------------------------------------------*/

	/*--------------------------------------------도서요청게시판--------------------------------------------*/

	// 도서요청 작성
	@GetMapping("/board/request/write")
	public String RequestBoardWrite(Model model, BoardRequestDto boardRequestDto) {
		return "board/request/write";
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
				model.addAttribute("resultMap", boardService.findByTitleContainingRequest(page, size, searchKeyword));
			} else {

				model.addAttribute("resultMap", boardService.findByContentContainingRequest(page, size, searchKeyword));
			}
		}
		System.out.println(boardService.findAll(page, size));
		return "board/request/list";
	}

	// 도서요청 저장
	@PostMapping("/board/request/write/action")
	public String requestBoardWriteAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
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

	// 도서요청 세부사항
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

	// 도서요청 삭제(복수)
	@PostMapping("/board/request/list/delete")
	public String smallLibraryBoardDelete(Model model, @RequestParam() Long[] deleteId, HttpServletRequest request)
			throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boardService.deleteAll(deleteId, myid, myRole, ip, WorkNumber.DELETE_REQUEST);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/request/list";
	}

	// 도서요청 삭제(단일)
	@PostMapping("/board/request/view/delete")
	public String requestBoardDetailViewDelete(Model model, @RequestParam() Long id, HttpServletRequest request)
			throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boardService.deleteById(id, myid, myRole, ip, WorkNumber.DELETE_REQUEST);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/request/list";
	}

	// 도서요청 수정
	@PostMapping("/board/request/view/action")
	public String requestBoardDetailViewAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boolean result = boardService.updateBoard(boardRequestDto, multiRequest, myid, myRole, ip,
					WorkNumber.UPDATE_REQUEST);

			if (!result) {
				throw new Exception("#Exception boardViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/request/list";
	}

	/*--------------------------------------------------------------------------------------------------*/

	/*--------------------------------------------작은도서관게시판--------------------------------------------*/

	// 작은도서관 소식 리스트
	@GetMapping("/small/smallList")
	public String getSmallLibraryBoardListPage(Model model, @RequestParam(value = "kind", required = false) String kind,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size) throws Exception {
		if (searchKeyword == null) {

			try {
				model.addAttribute("resultMap", boardService.findAllSmallLibrary(page, size));
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}

		} else {
			String findTitle = "제목";

			if (findTitle.equals(kind) == true) {
				model.addAttribute("resultMap", boardService.findByTitleContainingSmall(page, size, searchKeyword));
			} else {

				model.addAttribute("resultMap", boardService.findByContentContainingSmall(page, size, searchKeyword));
			}
		}
		System.out.println(boardService.findAll(page, size));
		return "small/smallList";
	}

	// 작은도서관 소식 작성
	@GetMapping("/small/smallWrite")
	public String smallLibraryBoardWrite(Model model, BoardRequestDto boardRequestDto) {
		return "small/smallWrite";
	}

	// 작은도서관 소식 삭제(복수)
	@PostMapping("/small/smallList/delete")
	public String requestBoardDelete(Model model, @RequestParam() Long[] deleteId, HttpServletRequest request)
			throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boardService.deleteAll(deleteId, myid, myRole, ip, WorkNumber.DELETE_SMALLLIBRARY);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/small/smallList";
	}

	// 작은도서관 소식 저장
	@PostMapping("/small/smallWrite/action")
	public String smallLibraryBoardWriteAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			Category category = categoryService.findCategory(15L);
			boardRequestDto.setCategory(category);
			if (!boardService.save(boardRequestDto, multiRequest, myid, myRole, ip, WorkNumber.CREATE_SMALLLIBRARY)) {
				throw new Exception("#Exception boardWriteAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/small/smallList";
	}

	// 작은도서관 소식 세부사항 보기
	@GetMapping("/small/smallView")
	public String smallLibraryBoardDetailView(Model model, BoardRequestDto boardRequestDto) throws Exception {
		try {
			if (boardRequestDto.getId() != null) {
				model.addAttribute("test", boardService.findById(boardRequestDto.getId()));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "small/smallView";
	}

	// 작은도서관 소식 수정
	@PostMapping("/small/smallView/action")
	public String smallLibraryBoardDetailViewAction(Model model, BoardRequestDto boardRequestDto,
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boolean result = boardService.updateBoard(boardRequestDto, multiRequest, myid, myRole, ip,
					WorkNumber.UPDATE_SMALLLIBRARY);

			if (!result) {
				throw new Exception("#Exception boardViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/small/smallList";
	}

	// 작은도서관 소식 삭제(단일)
	@PostMapping("/small/smallView/delete")
	public String smallLibraryBoardDetailViewDelete(Model model, @RequestParam() Long id, HttpServletRequest request)
			throws Exception {
		HttpSession mySession = request.getSession();
		String myid = (String) mySession.getAttribute("id");
		Role myRole = (Role) mySession.getAttribute("Role");
		String ip = (String) mySession.getAttribute("ipaddress");
		try {
			boardService.deleteById(id, myid, myRole, ip, WorkNumber.DELETE_SMALLLIBRARY);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/small/smallList";
	}

	// 작은도서관 소개
	@GetMapping("/small/small")
	public String SmallLibrary() {
		return "small/smallLibrary";
	}

	/*----------------------------------------------------------------------------------------------------*/

	/*-----------------------------------------------댓글 작성-----------------------------------------------*/

	// 댓글 작성 [22-12-07]
	@PostMapping("/board/reply")
	public String boardReplyWriteAction(Model model, @ModelAttribute BoardReplyRequestDto boardReplyRequestDto,
			Principal principal) throws Exception {
		System.out.println(boardReplyRequestDto);
		try {
			String userId = principal.getName();
			String id = memberService.findByEmail(userId).getId();

			Category category = categoryService.findCategory(11L);

			boardReplyRequestDto.setRegisterId(id);
			boardReplyRequestDto.setCategory(category);
			boardService.save(boardReplyRequestDto);

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return "redirect:/board/view?id=" + boardReplyRequestDto.getBlevel();
	}

	// 댓글 삭제
	@DeleteMapping("/board/reply/delete")
	public String boardReplyDelete(Model model, @RequestParam("deleteId") Long id, @RequestParam("infoId") Long boardId)
			throws Exception {
		boardService.deleteById(id);
		return "redirect:/board/view?id=" + boardId;
	}

	// 댓글 수정
	/*
	 * @PutMapping(value = "/replyUpdate") public String boardreplyUpdate(Model
	 * model, @RequestBody BoardReplyRequestDto boardReplyRequestDto) throws
	 * Exception { String boardId; // 변수 생성 try { boardId =
	 * boardService.updateReply(boardReplyRequestDto); // 값을 초기화 } catch (Exception
	 * e) { throw new Exception(e.getMessage()); } return "redirect:/board/view?id="
	 * + boardId; }
	 */

	/*-----------------------------------------------------------------------------------------------------*/

}
