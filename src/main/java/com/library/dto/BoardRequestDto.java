package com.library.dto;

import java.time.LocalDateTime;

import com.library.entity.Board;
import com.library.entity.Category;
import com.library.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDto {
	private Long id;
	private Long blevel;			// Long 타입변경[2022-11-30]
	private String title;
	private String content;
	private int readCnt;
	private Category category;
	private String registerId;
	
	public Board toEntity() {
		return Board.builder()
				.id(id)
				.blevel(blevel)
				.title(title)
				.content(content)
				.readCnt(readCnt)
				.category(category)
				.registerId(registerId)
				.build();	
	}
	
	@Override
	public String toString() {
		return "BoardRequestDto [id=" + id + ", title=" + title + ", content=" + content + ", registerId=" + registerId
				+ "]";
	}



}
