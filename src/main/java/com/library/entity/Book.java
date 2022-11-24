package com.library.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "book")
@Getter
@Setter
@ToString
public class Book {
	@Id
	private Long bookNumber;
	
	private String bookname;
	
	private String authors;
	
	@NotNull
	private String isbn13;
	
	private String bookImageURL;
}
