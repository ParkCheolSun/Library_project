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
	private String contents;
	private LocalDateTime cDate;
	private String disclosure;
	private Member member;
	private Category category;
	private int readCnt;
	private String registerId;

	public BoardResponseDto(Board entity) {
		this.member = entity.getMember();
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.contents = entity.getContents();
		this.cDate = entity.getCDate();
		this.disclosure = entity.getDisclosure();
		this.readCnt = entity.getReadCnt();
		this.registerId = entity.getRegisterId();
	}

	@Override
	public String toString() {
		return "BoardResponseDto [id=" + id + ", title=" + title + ", contents=" + contents + ", readCnt=" + readCnt
				+ ", registerId=" + registerId + ", cDate=" + cDate + "]";
	}

	public String getRegisterTime() {
		return toStringDateTime(this.cDate);
	}

	public static String toStringDateTime(LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return Optional.ofNullable(localDateTime).map(formatter::format).orElse("");
	}
}
