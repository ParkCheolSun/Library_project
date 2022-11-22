package com.library.dto;

import javax.persistence.Entity;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Data;

@Data
public class BookDto {
	@NotNull
	String bookname;
	@NotNull
	String authors;
	@NotNull
	String bookImageURL;
	
	public BookDto(String bookname, String authors, String bookImageURL) {
		this.bookname = bookname;
		this.authors = authors;
		this.bookImageURL = bookImageURL;
	}
}
