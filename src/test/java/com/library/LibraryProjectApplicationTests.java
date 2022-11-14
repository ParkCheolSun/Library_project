package com.library;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.library.dto.BoardRequestDto;
import com.library.dto.BoardResponseDto;
import com.library.service.BoardService;

@SpringBootTest
class LibraryProjectApplicationTests {
	
	@Autowired
	private BoardService boardService;

	@Test
	void contextLoads() {
		BoardRequestDto boardSaveDto = new BoardRequestDto();
		
		boardSaveDto.setTitle("제목입니다.");
		boardSaveDto.setContents("내용입니다.");
		boardSaveDto.setHits(20);
		boardSaveDto.setCDate(LocalDateTime.now());
		
		
		Long result = boardService.save(boardSaveDto);
		
		if(result > 0) {
			System.out.println("성공입니다.");
			findAll();
			
		}else {
			System.out.println("실패입니다.");
		}
	}
	
	void findAll() {
		List<BoardResponseDto>list = boardService.findAll();
		
		if(list != null) {
			System.out.println("findAll()성공 : " + list.toString());
		}else {
			System.out.println("실패했습니다.");
		}
	}
	
	void findById(Long member) {
		BoardResponseDto info = boardService.findById(member);
		
		if(info != null) {
			System.out.println("findById() 성공 : " + info.toString());
			updateBoard(member);
		}else {
			System.out.println("실패하였습니다.");
		}
	}
	
	void updateBoard(Long member) {
		BoardRequestDto boardRequestDto = new BoardRequestDto();
		
		boardRequestDto.setTitle("업데이트 제목");
		boardRequestDto.setContents("업데이트 내용");
		
		int result = boardService.updateBoard(boardRequestDto);
		
		if(result > 0) {
			System.out.println("보드 업데이트 성공");
		}else {
			System.out.println("보드 업데이트 실패");
		}
	}

}
