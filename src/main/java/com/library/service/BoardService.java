package com.library.service;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import com.library.dto.BoardRequestDto;
import com.library.dto.BoardResponseDto;
import com.library.entity.Board;
import com.library.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	private final BoardFileService boardFileService;

	@Transactional
	public boolean save(BoardRequestDto boardRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {

		Board result = boardRepository.save(boardRequestDto.toEntity());

		boolean resultFlag = false;

		if (result != null) {
			boardFileService.uploadFile(multiRequest, result.getId());
			resultFlag = true;
		}

		return resultFlag;
	}

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

	public HashMap<String, Object> findById(Long id) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		boardRepository.updateBoardReadCntInc(id);

		BoardResponseDto info = new BoardResponseDto(boardRepository.findById(id).get());

		resultMap.put("info", info);
		resultMap.put("fileList", boardFileService.findByBoardId(info.getId()));

		return resultMap;
	}

	public boolean updateBoard(BoardRequestDto boardRequestDto, MultipartHttpServletRequest multiRequest)
			throws Exception {

		int result = boardRepository.updateBoard(boardRequestDto);

		boolean resultFlag = false;

		if (result > 0) {
			boardFileService.uploadFile(multiRequest, boardRequestDto.getId());
			resultFlag = true;
		}

		return resultFlag;
	}

	public void deleteById(Long id) throws Exception {
		Long[] idArr = { id };
		boardFileService.deleteBoardFileYn(idArr);
		boardRepository.deleteById(id);
	}

	public void deleteAll(Long[] deleteIdList) throws Exception {
		boardFileService.deleteBoardFileYn(deleteIdList);
		boardRepository.deleteBoard(deleteIdList);
	}
}
