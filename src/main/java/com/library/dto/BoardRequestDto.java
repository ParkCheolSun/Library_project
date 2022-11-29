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
	private String content;
	private int readCnt;
	private Boolean disclosure;    // 공개여부  String >> Boolean 타입변경[2022-11-22]
	private Member member;
	private Category category;
	private String registerId;
	
	public Board toEntity() {
		if(disclosure==null)		// null값을 강제로 false으로 변환 [2022-11-22]
			disclosure = false;
		return Board.builder()
				.member(member)
				.id(id)
				.blevel(blevel)
				.title(title)
				.content(content)
				.readCnt(readCnt)
				.disclosure(disclosure)
				.category(category)
				.registerId(registerId)
				.build();	
	}
	
	@Override
	public String toString() {
		return "BoardRequestDto [id=" + id + ", title=" + title + ", content=" + content + ", registerId=" + registerId
				+ ", disclosure=" + disclosure + "]";
	}



}
