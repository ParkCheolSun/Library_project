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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;

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
	private Long blevel; // 깊이(댓글)		// Long 타입변경[2022-11-30]
	
	
	@NotNull
	@NotEmpty(message = "제목은 필수입니다.")
	private String title; // 제목

	@Column(length = 1000)
	private String content; // 내용

//	private LocalDateTime cDate; // 작성날짜
//
//	private LocalDateTime mDate; // 수정날짜

	private int readCnt; // 조회수

	@NotNull
	@Column(nullable = false)
	private Boolean disclosure = false; 	// 공개여부 String >> Boolean 타입변경[2022-11-22]

	private String registerId; // 작성자

	public boolean getDisclosure() {
		if(disclosure == null)
			disclosure = false;
		return disclosure; 					// disclosure 체크를 하지 않았을 경우 false / 이외 true [2022-11-22]
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "mNumber")
	private Member member;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@Builder
	public Board(Long id, Long blevel, String title, String content, int readCnt, Boolean disclosure, Member member,
			Category category ,String registerId) {
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
