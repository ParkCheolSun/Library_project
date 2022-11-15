package com.library.entity;

import java.time.LocalDateTime;

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
public class Board extends BaseTimeEntity{
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

	private LocalDateTime cDate; // 작성날짜

	private LocalDateTime mDate; // 수정날짜

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
	public Board(Long id, short blevel, String title, String content, LocalDateTime cDate, LocalDateTime mDate,
			int readCnt, String disclosure, Member member, String registerId) {
		this.member = member;
		this.id = id;
		this.blevel = blevel;
		this.title = title;
		this.content = content;
		this.cDate = cDate;
		this.mDate = mDate;
		this.readCnt = readCnt; // hits == cnt 
		this.disclosure = disclosure;
		this.member = member;
		this.category = category;
		this.registerId = registerId;
	}
}
