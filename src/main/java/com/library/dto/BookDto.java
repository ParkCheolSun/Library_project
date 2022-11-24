package com.library.dto;

import java.util.ArrayList;
import java.util.List;

import com.library.entity.Book;

import lombok.Data;

@Data
public class BookDto {
	private Long bookNumber;
	private String bookName;
	private String authors;
	private String isbn13;
	private String bookImageURL;
	
	public BookDto(Long bookNumber, String bookname, String authors, String isbn13, String bookImageURL) {
		this.bookNumber = bookNumber;
		this.bookName = bookname;
		this.authors = authors;
		this.isbn13 = isbn13;
		this.bookImageURL = bookImageURL;
	}
	
	public static List<BookDto> CreateBookDtoList(List<Book> bookList) {
		List<BookDto> bookDtoList = new ArrayList<BookDto>();
		for(Book book : bookList) {
			BookDto bookDto = new BookDto(
					book.getBookNumber(), 
					book.getBookname(), 
					book.getAuthors(), 
					book.getIsbn13(), 
					book.getBookImageURL());
			bookDtoList.add(bookDto);
		}
		return bookDtoList;
	}
}
