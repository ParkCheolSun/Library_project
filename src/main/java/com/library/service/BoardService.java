package com.library.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.dto.BoardRequestDto;
import com.library.dto.BoardResponseDto;
import com.library.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;

	@Transactional
	public Long save(BoardRequestDto boardSaveDto) {
		return boardRepository.save(boardSaveDto.toEntity()).getNumber();
	}

	@Transactional(readOnly = true)
	public List<BoardResponseDto> findAll() {
		return boardRepository.findAll().stream().map(BoardResponseDto::new).collect(Collectors.toList());
	}

	public BoardResponseDto findById(Long member) {
		return new BoardResponseDto(boardRepository.findById(member).get());
	}

	public int updateBoard(BoardRequestDto boardRequestDto) {
		return boardRepository.updateBoard(boardRequestDto);
	}

	public void deleteById(Long member) {
		boardRepository.deleteById(member);
	}

}
