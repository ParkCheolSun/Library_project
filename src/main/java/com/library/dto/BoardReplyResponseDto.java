package com.library.dto;

import com.library.entity.Board;
import com.library.entity.Member;

import lombok.Getter;

@Getter
public class BoardReplyResponseDto {

	private String content;
	private String member_id;
	private String registerId;

	public BoardReplyResponseDto(Board entity) {
		this.content = entity.getContent();
		this.member_id = entity.getMember_id();
		this.registerId = entity.getRegisterId();
		
	}
	
	
}