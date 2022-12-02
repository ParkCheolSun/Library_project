package com.library.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.library.dto.BookDto;
import com.library.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
	private final BookRepository bookRepository;
	private final List<BookDto> bookDtoListToLib;
	
	public List<BookDto> popularityBookToLibrary(){
		bookDtoListToLib.clear();
		bookDtoListToLib.addAll(BookDto.CreateBookDtoList(bookRepository.findAll()));
		return bookDtoListToLib;
	}
}
