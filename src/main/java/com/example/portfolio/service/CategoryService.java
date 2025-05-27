package com.example.portfolio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.portfolio.entity.Category;
import com.example.portfolio.entity.User;
import com.example.portfolio.repository.CategoryRepository;
import com.example.portfolio.repository.TaskRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
    @Autowired
    private TaskRepository taskRepository;
	
	public Optional<Category> findById(Long categoryId) {
		return categoryRepository.findById(categoryId);
	}
	
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
	
	public void deleteById(Long id) {
	    categoryRepository.deleteById(id);
	}

	public Category save(Category category) {
	    return categoryRepository.save(category);
	}

	public boolean hasTasksLinked(Long categoryId) {
	    return categoryRepository.findById(categoryId)
	        .map(category -> taskRepository.findAll().stream()
	            .anyMatch(task -> task.getCategory() != null && 
	                              task.getCategory().getId().equals(categoryId)))
	        .orElse(false);
	}
	
	public List<Category> findByUser(User user) {
	    return categoryRepository.findByUser(user);
	}

	public List<Category> findForUser(User user) {
	    return categoryRepository.findByUserOrSharedTrue(user);
	}


}
