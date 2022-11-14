package com.library.dto;

import java.time.LocalDateTime;

import com.library.entity.Board;
import com.library.entity.Category;
import com.library.entity.Member;

import lombok.Getter;

@Getter
public class BoardResponseDto {
	private Long number;
	private String title;
	private String contents;
	private LocalDateTime cDate;
	private String disclosure;
	private Member member;
	private Category category;
	private int hits;
	
	public BoardResponseDto(Board entity) {
		this.member = entity.getMember();
		this.number = entity.getNumber();
		this.title = entity.getTitle();
		this.contents = entity.getContents();
		this.cDate = entity.getCDate();
		this.disclosure = entity.getDisclosure();
		this.hits = entity.getHits();
	}
	
	@Override
	public String toString() {
		return "BoardResponseDto [id=" + member + ", number=" + number + ", title=" + title + ", contents=" + contents + ", cDate=" + cDate
				+ ", disclosure=" + disclosure +", hits=" + hits + " ]"; 
	}
}
