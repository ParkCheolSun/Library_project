package com.library.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.library.constant.Role;
import com.library.constant.WorkNumber;
import com.library.dto.BoardReplyRequestDto;
import com.library.dto.BoardRequestDto;
import com.library.dto.BoardResponseDto;
import com.library.entity.Board;
import com.library.entity.BoardFile;
import com.library.entity.Category;
import com.library.entity.Member;
import com.library.entity.MemberLog;
import com.library.repository.BoardRepository;
import com.library.repository.MemberLogRepository;
import com.library.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	private final MemberLogRepository memberLogRepository;
	private final BoardFileService boardFileService;

	// 게시판 저장
	@Transactional
	public boolean save(BoardRequestDto boardRequestDto, MultipartHttpServletRequest multiRequest, String myid,
			Role myRole, String ip, WorkNumber wNum) throws Exception {

		String contents;
		Member mem = memberRepository.findById(myid);
		if (mem == null) {
			contents = "해당 사용자는 존재하지 않습니다.";
			MemberLog memLog = MemberLog.createMemberLog(wNum, contents, myid, myRole, ip);
			memberLogRepository.save(memLog);
		}
		Board result = boardRepository.save(boardRequestDto.toEntity());

		contents = "존재하지 않는 작업입니다.";
		switch (wNum) {
		case CREATE_NOTICE:
			contents = "고유번호[" + result.getId() + "] 공지사항 글 생성 완료";
			break;
		case CREATE_FREE:
			contents = "고유번호[" + result.getId() + "] 자유게시판 글 생성 완료";
			break;
		case CREATE_SUGGESTION:
			contents = "고유번호[" + result.getId() + "] 건의사항 글 생성 완료";
			break;
		case CREATE_REQUEST:
			contents = "고유번호[" + result.getId() + "] 도서요청 글 생성 완료";
			break;
		case CREATE_FAQ:
			contents = "고유번호[" + result.getId() + "] FAQ 글 생성 완료";
			break;
		case CREATE_SMALLLIBRARY:
			contents = "고유번호[" + result.getId() + "] 작은도서관 글 생성 완료";
			break;
		default:
			break;
		}
		MemberLog memLog = MemberLog.createMemberLog(wNum, contents, myid, myRole, ip);
		memberLogRepository.save(memLog);

		boolean resultFlag = false;

		if (result != null) {
			boardFileService.uploadFile(multiRequest, result);
			resultFlag = true;
		}

		return resultFlag;
	}

	// 게시판 출력
	@Transactional(readOnly = true)
	public HashMap<String, Object> findAll(Integer page, Integer size) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Page<Board> list = boardRepository
				.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "registerTime")));

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 공지사항 게시판 출력
	@Transactional(readOnly = true)
	public HashMap<String, Object> findAllNotice(Integer page, Integer size) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(10l);
		Page<Board> list = boardRepository
				.findAllByCategory(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "registerTime")), category);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 자주하는 질문 게시판 출력
	@Transactional(readOnly = true)
	public HashMap<String, Object> findAllFaq(Integer page, Integer size) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(12l);
		Page<Board> list = boardRepository
				.findAllByCategory(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "registerTime")), category);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 건의사항 게시판 출력
	@Transactional(readOnly = true)
	public HashMap<String, Object> findAllSuggestion(Integer page, Integer size) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(13l);
		Page<Board> list = boardRepository
				.findAllByCategory(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "registerTime")), category);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 자유게시판 출력
	@Transactional(readOnly = true)
	public HashMap<String, Object> findAllBoard(Integer page, Integer size) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(11l);
		Page<Board> list = boardRepository.findAllByCategoryAndBlevel(
				PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "registerTime")), category, null);
		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 도서요청 게시판 출력
	@Transactional(readOnly = true)
	public HashMap<String, Object> findAllRequest(Integer page, Integer size) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(14l);
		Page<Board> list = boardRepository
				.findAllByCategory(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "registerTime")), category);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 작은도서관 소식 게시판 출력
	@Transactional(readOnly = true)
	public HashMap<String, Object> findAllSmallLibrary(Integer page, Integer size) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(15l);
		Page<Board> list = boardRepository
				.findAllByCategory(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "registerTime")), category);
		System.out.println("list : " + list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 제목으로 게시글 찾기
	@Transactional
	public HashMap<String, Object> findByTitleContaining(Integer page, Integer size, String searchKeyword) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Board> list = boardRepository.findByTitleContaining(pageable, searchKeyword);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 공지사항 - 제목으로 게시글 찾기
	@Transactional
	public HashMap<String, Object> findByTitleContainingNotice(Integer page, Integer size, String searchKeyword) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(10l);
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Board> list = boardRepository.findByTitleContainingAndCategory(pageable, searchKeyword, category);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 도서요청 - 제목으로 게시글 찾기
	@Transactional
	public HashMap<String, Object> findByTitleContainingRequest(Integer page, Integer size, String searchKeyword) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(14l);
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Board> list = boardRepository.findByTitleContainingAndCategory(pageable, searchKeyword, category);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 자유게시판 - 제목으로 게시글 찾기
	@Transactional
	public HashMap<String, Object> findByTitleContainingBoard(Integer page, Integer size, String searchKeyword) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(11l);
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Board> list = boardRepository.findByTitleContainingAndCategory(pageable, searchKeyword, category);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 자주하는질문 - 제목으로 게시글 찾기
	@Transactional
	public HashMap<String, Object> findByTitleContainingFaq(Integer page, Integer size, String searchKeyword) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(12l);
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Board> list = boardRepository.findByTitleContainingAndCategory(pageable, searchKeyword, category);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 건의사항 - 제목으로 게시글 찾기
	@Transactional
	public HashMap<String, Object> findByTitleContainingSuggestion(Integer page, Integer size, String searchKeyword) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(13l);
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Board> list = boardRepository.findByTitleContainingAndCategory(pageable, searchKeyword, category);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 작은도서관 소식 - 제목으로 게시글 찾기
	@Transactional
	public HashMap<String, Object> findByTitleContainingSmall(Integer page, Integer size, String searchKeyword) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(15l);
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Board> list = boardRepository.findByTitleContainingAndCategory(pageable, searchKeyword, category);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 내용으로 게시글 찾기
	@Transactional
	public HashMap<String, Object> findByContentContaining(Integer page, Integer size, String searchKeyword) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Board> list = boardRepository.findByContentContaining(pageable, searchKeyword);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 공지사항 - 내용으로 게시글 찾기
	@Transactional
	public HashMap<String, Object> findByContentContainingNotice(Integer page, Integer size, String searchKeyword) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(10l);
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Board> list = boardRepository.findByContentContainingAndCategory(pageable, searchKeyword, category);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 도서요청 - 내용으로 게시글 찾기
	@Transactional
	public HashMap<String, Object> findByContentContainingRequest(Integer page, Integer size, String searchKeyword) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(14l);
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Board> list = boardRepository.findByContentContainingAndCategory(pageable, searchKeyword, category);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 자유게시판 - 내용으로 게시글 찾기
	@Transactional
	public HashMap<String, Object> findByContentContainingBoard(Integer page, Integer size, String searchKeyword) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(11l);
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Board> list = boardRepository.findByContentContainingAndCategory(pageable, searchKeyword, category);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 자주하는질문 - 내용으로 게시글 찾기
	@Transactional
	public HashMap<String, Object> findByContentContainingFaq(Integer page, Integer size, String searchKeyword) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(12l);
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Board> list = boardRepository.findByContentContainingAndCategory(pageable, searchKeyword, category);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 건의사항 - 내용으로 게시글 찾기
	@Transactional
	public HashMap<String, Object> findByContentContainingSuggestion(Integer page, Integer size, String searchKeyword) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(13l);
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Board> list = boardRepository.findByContentContainingAndCategory(pageable, searchKeyword, category);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 작은도서관 소식 - 내용으로 게시글 찾기
	@Transactional
	public HashMap<String, Object> findByContentContainingSmall(Integer page, Integer size, String searchKeyword) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Category category = new Category();
		category.setCategory_id(15l);
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Board> list = boardRepository.findByContentContainingAndCategory(pageable, searchKeyword, category);

		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());

		return resultMap;
	}

	// 게시글 상세내용 출력
	public HashMap<String, Object> findById(Long id) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		boardRepository.updateBoardReadCntInc(id);

		BoardResponseDto info = new BoardResponseDto(boardRepository.findById(id).get());
		resultMap.put("info", info);

		// 댓글
		List<Board> reply = boardRepository.findAllByBlevel(id);
		resultMap.put("reply", reply);

		List<BoardFile> fileList = boardFileService.findByBoardId(info.getId());
		if (!fileList.isEmpty()) {
			resultMap.put("fileList", fileList);
		} else {
			resultMap.put("fileList", "empty");
		}
		return resultMap;
	}

	// 댓글 작성 [22-12-07]
	@Transactional
	public void save(BoardReplyRequestDto boardReplyRequestDto) throws Exception {
		Board result = boardRepository.save(boardReplyRequestDto.toEntity());
	}

	// 댓글 삭제 [22-12-08]
	public void deleteById(Long id) {
		boardRepository.deleteById(id);
	}

	// 댓글 수정 [22-12-08]
	/*
	 * public String updateReply(BoardReplyRequestDto boardReplyRequestDto) throws
	 * Exception { Board result =
	 * boardRepository.save(boardReplyRequestDto.toEntity()); if(result.getBlevel()
	 * != null) { String content = "content:" + result.getBlevel() + "수정 완료"; }
	 * return String.valueOf(result.getId()); }
	 */

	// BoardRequestDto -> Optional<Board> 변환
	public Board change(Optional<Board> optional, BoardRequestDto boardRequestDto) {
		Board ori = optional.get();
		ori.setContent(boardRequestDto.getContent());
		ori.setRegisterId(boardRequestDto.getRegisterId());
		ori.setTitle(boardRequestDto.getTitle());
		return ori;
	}

	// 게시글 수정
	public boolean updateBoard(BoardRequestDto boardRequestDto, MultipartHttpServletRequest multiRequest, String myid,
			Role myRole, String ip, WorkNumber wNum) throws Exception {
		Board temp = change(boardRepository.findById(boardRequestDto.getId()), boardRequestDto);
		Board result = boardRepository.save(temp);

		String contents = "존재하지 않는 작업입니다.";
		switch (wNum) {
		case UPDATE_NOTICE:
			contents = "고유번호[" + boardRequestDto.getId() + "] 공지사항 글 수정 완료";
			break;
		case UPDATE_FREE:
			contents = "고유번호[" + boardRequestDto.getId() + "] 자유게시판 글 수정 완료";
			break;
		case UPDATE_SUGGESTION:
			contents = "고유번호[" + boardRequestDto.getId() + "] 건의사항 글 수정 완료";
			break;
		case UPDATE_REQUEST:
			contents = "고유번호[" + boardRequestDto.getId() + "] 도서요청 글 수정 완료";
			break;
		case UPDATE_FAQ:
			contents = "고유번호[" + boardRequestDto.getId() + "] FAQ 글 수정 완료";
			break;
		case UPDATE_SMALLLIBRARY:
			contents = "고유번호[" + boardRequestDto.getId() + "] 작은도서관 글 수정 완료";
			break;
		default:
			break;
		}
		MemberLog memLog = MemberLog.createMemberLog(wNum, contents, myid, myRole, ip);
		memberLogRepository.save(memLog);

		boolean resultFlag = false;

		if (result.getId() != null) {
			boardFileService.uploadFile(multiRequest, result);
			resultFlag = true;
		}

		return resultFlag;
	}

	// 게시글 삭제
	public void deleteById(Long id, String myid, Role myRole, String ip, WorkNumber wNum) throws Exception {
		Long[] idArr = { id };
		boardFileService.deleteBoardFileYn(idArr);
		boardRepository.deleteById(id);
		String contents = "존재하지 않는 작업입니다.";
		switch (wNum) {
		case DELETE_NOTICE:
			contents = "고유번호[" + id + "] 공지사항 글 삭제 완료";
			break;
		case DELETE_FREE:
			contents = "고유번호[" + id + "] 자유게시판 글 삭제 완료";
			break;
		case DELETE_SUGGESTION:
			contents = "고유번호[" + id + "] 건의사항 글 삭제 완료";
			break;
		case DELETE_REQUEST:
			contents = "고유번호[" + id + "] 도서요청 글 삭제 완료";
			break;
		case DELETE_FAQ:
			contents = "고유번호[" + id + "] FAQ 글 삭제 완료";
			break;
		case DELETE_SMALLLIBRARY:
			contents = "고유번호[" + id + "] 작은도서관 글 삭제 완료";
			break;
		default:
			break;
		}
		MemberLog memLog = MemberLog.createMemberLog(wNum, contents, myid, myRole, ip);
		memberLogRepository.save(memLog);
	}

	// 선택한 게시글 모두 삭제(관리자, 매니저)
	public void deleteAll(Long[] deleteIdList, String myid, Role myRole, String ip, WorkNumber wNum) throws Exception {
		boardFileService.deleteBoardFileYn(deleteIdList);
		boardRepository.deleteBoard(deleteIdList);
		for (Long num : deleteIdList) {
			String contents = "고유번호[" + num + "] 게시판 삭제 완료";
			MemberLog memLog = MemberLog.createMemberLog(wNum, contents, myid, myRole, ip);
			memberLogRepository.save(memLog);
		}
	}
}
