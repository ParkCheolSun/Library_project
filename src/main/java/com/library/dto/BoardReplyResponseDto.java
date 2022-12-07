package com.library.dto;

import com.library.entity.Board;
import com.library.entity.Member;

import lombok.Getter;

@Getter
public class BoardReplyResponseDto {

	private String content;
	private Member member;
	private String registerId;

	public BoardReplyResponseDto(Board entity) {
		this.content = entity.getContent();
		this.member = entity.getMember();
		this.registerId = entity.getRegisterId();
		
	}
	
	
}