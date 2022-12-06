package com.library.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookResponseDto {
	String bookName;
	String authors;
	String publisher;
	String publication;
	String isbn;
	String bookImageURL;
	
	public BookResponseDto(String bookName, String authors, String publisher, String publication, String isbn, String bookImageURL) {
		this.bookName = bookName;
		this.authors = authors;
		this.publisher = publisher;
		this.publication = publication;
		this.isbn = isbn;
		this.bookImageURL = bookImageURL;
	}
}
