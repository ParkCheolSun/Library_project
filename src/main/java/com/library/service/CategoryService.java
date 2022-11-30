package com.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.library.entity.Category;
import com.library.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;
	
	public boolean findAll() {
		List<Category> clist = categoryRepository.findAll();
		if(clist.isEmpty())
			return false;
		return true;
	}
	
	public boolean saveCategory(Category category) {
		Category cate = categoryRepository.save(category);
		if(cate.getCategory_id() != 0) {
			return true;
		}
		return false;
	}
	
	public Category findCategory(Long category_id) {
		Optional<Category> temp = categoryRepository.findById(category_id);
		if(temp.isEmpty()) {
			System.out.println("Category 테이블에 해당 id가 존재하지 않습니다.");
		}
		return temp.get();
	}
	
}
