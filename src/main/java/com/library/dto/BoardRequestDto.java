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
	private Long number;
	private short blevel;
	private String title;
	private String contents;
	private LocalDateTime cDate;
	private LocalDateTime mDate;
	private int hits;
	private String disclosure;
	private Member member;
	private Category category;
	
	public Board toEntity() {
		return Board.builder()
				.member(member)
				.number(number)
				.blevel(blevel)
				.title(title)
				.contents(contents)
				.cDate(cDate)
				.mDate(mDate)
				.hits(hits)
				.disclosure(disclosure)
				.build();			
	}
	
	@Override
	public String toString() {
		return "BoardRequestDto [id = " + member + ", number=" + number + ","
				+ "title=" + title + ", contents=" + contents + "]";
	}



}
