package com.example.portfolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.portfolio.entity.Category;
import com.example.portfolio.entity.User;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	List<Category> findByUser(User user);

	
	@Query("SELECT c FROM Category c WHERE c.user = :user OR c.shared = true")
	List<Category> findForUserOrShared(@Param("user") User user);

}
