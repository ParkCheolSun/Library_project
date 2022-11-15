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
	private short blevel;
	private String title;
	private String contents;
	private LocalDateTime cDate;
	private LocalDateTime mDate;
	private int readCnt;
	private String disclosure;
	private Member member;
	private Category category;
	private String registerId;
	
	public Board toEntity() {
		return Board.builder()
				.member(member)
				.id(id)
				.blevel(blevel)
				.title(title)
				.contents(contents)
				.cDate(cDate)
				.mDate(mDate)
				.readCnt(readCnt)
				.disclosure(disclosure)
				.registerId(registerId)
				.build();	
	}
	
	@Override
	public String toString() {
		return "BoardRequestDto [id=" + id + ", title=" + title + ", contents=" + contents + ", registerId=" + registerId
				+ "]";
	}



}
