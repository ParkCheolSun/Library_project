package com.library.dto;

import com.library.entity.Board;
import com.library.entity.Category;
import com.library.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardReplyRequestDto {
	private Long blevel;			// Long 타입변경[2022-11-30]
	private String replyContent;
	private String replytitle;
	private String registerId;
	private Category category;
	
	public Board toEntity() {
		return Board.builder()
				.blevel(blevel)
				.content(replyContent)
				.registerId(registerId)
				.title(replytitle)
				.category(category)
				.build();	
	}
	



}
