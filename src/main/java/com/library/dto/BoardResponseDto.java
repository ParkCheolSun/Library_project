package com.library.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.library.entity.Board;
import com.library.entity.Category;
import com.library.entity.Member;

import lombok.Getter;

@Getter
public class BoardResponseDto {
	private Long id;
	private String title;
	private String content;
	private LocalDateTime registerTime;
	private Boolean disclosure;         // 공개여부  String >> Boolean 타입변경[2022-11-22]
	private String member_id;
	private Category category;
	private int readCnt;
	private String registerId;

	public BoardResponseDto(Board entity) {
		this.member_id = entity.getMember_id();
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.registerTime = entity.getRegisterTime();
		this.disclosure = entity.getDisclosure();   
		this.readCnt = entity.getReadCnt();
		this.registerId = entity.getRegisterId();
		
	}

	@Override
	public String toString() {
		return "BoardResponseDto [id=" + id + ", title=" + title + ", content=" + content + ", readCnt=" + readCnt
				+ ", registerId=" + registerId + ", registerTime=" + registerTime + "]";
	}

	public static String toStringDateTime(LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return Optional.ofNullable(localDateTime).map(formatter::format).orElse("");
	}
}
