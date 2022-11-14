package com.library.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "attach")
@Getter
@Setter
@ToString
public class Attach {
	@Id
	@Column(name = "ata_number")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ata_number;
	
	@NotNull
	private String ata_fileName;
	
	@NotNull
	private LocalDateTime ata_date;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;
}
