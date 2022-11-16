package com.library.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.library.constant.Role;
import com.library.dto.BoardRequestDto;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Entity
@Table(name = "board")
@Getter
@Setter
@ToString
public class Board extends BaseEntity {
	@Id
	@Column(name = "board_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ColumnDefault("0")
	private short blevel; // 깊이(댓글)

	@NotNull
	private String title; // 제목

	private String content; // 내용

//	private LocalDateTime cDate; // 작성날짜
//
//	private LocalDateTime mDate; // 수정날짜

	private int readCnt; // 조회수

	private String disclosure; // 공개여부

	private String registerId; // 작성자

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "mNumber")
	private Member member;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@Builder
	public Board(Long id, short blevel, String title, String content, int readCnt, String disclosure, Member member,
			String registerId) {
		this.member = member;
		this.id = id;
		this.blevel = blevel;
		this.title = title;
		this.content = content;
		this.readCnt = readCnt; // hits == cnt
		this.disclosure = disclosure;
		this.member = member;
		this.category = category;
		this.registerId = registerId;
	}

	public static Board createBoard(BoardRequestDto boardDto) {
		Board board = new Board();
		board.setId(boardDto.getId());
		board.setBlevel(boardDto.getBlevel());
		board.setCategory(boardDto.getCategory());
		board.setContent(boardDto.getContent());
		board.setDisclosure(boardDto.getDisclosure());
		board.setMember(boardDto.getMember());
		board.setReadCnt(boardDto.getReadCnt());
		board.setRegisterId(boardDto.getRegisterId());
		board.setTitle(boardDto.getTitle());
		return board;
	}
}
