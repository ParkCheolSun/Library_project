package com.library.dto;

import com.library.entity.Board;
import com.library.entity.Member;

import lombok.Getter;

@Getter
public class BoardReplyResponseDto {

	private Long id;
	private String content;
	private Member member;
	private String registerId;

	public BoardReplyResponseDto(Board entity) {
		this.id = entity.getId();
		this.content = entity.getContent();
		this.member = entity.getMember();
		this.registerId = entity.getRegisterId();
		
	}
	
	
}