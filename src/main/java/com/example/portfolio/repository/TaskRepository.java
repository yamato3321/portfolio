package com.example.portfolio.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.portfolio.entity.Task;
import com.example.portfolio.entity.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
	Page<Task> findByUser(User user, Pageable pageable);
	
//	Page<Task> findByUserAndCompleted(User user,boolean completed,Pageable pageble);
	
	Page<Task> findByUserAndTitleContainingIgnoreCaseOrUserAndDescriptionContainingIgnoreCase(
		        User user1, String title,
		        User user2, String description,
		        Pageable pageable
		    );
	
    Page<Task> findByUserAndCategoryIdAndTitleContainingIgnoreCaseAndCompleted(
            User user,
            Long categoryId,
            String keyword,
            boolean completed,
            Pageable pageable
        );
    
    Page<Task> findByUserAndTitleContainingIgnoreCaseAndCompleted(
    	    User user, 
    	    String keyword, 
    	    boolean completed, 
    	    Pageable pageable
    	);
    
    Page<Task> findByUserAndCategoryIdAndCompleted(
    	    User user, 
    	    Long categoryId, 
    	    boolean completed, 
    	    Pageable pageable
    	);

    Page<Task> findByUserAndCompleted(
    	    User user, 
    	    boolean completed, 
    	    Pageable pageable
    	);

//	Page<Task> findByUserAndCompletedAndTitleContainingIgnoreCaseOrUserAndCompletedAndDescriptionContainingIgnoreCase(
//				User user1,boolean completed1,String title,
//				User user2,boolean completed2,String description,
//				Pageable pageable
//			);
//	
	
    List<Task> findByUserOrderByDeadlineAsc(User user);
    
    List<Task> findByUserOrderByDeadlineDesc(User user);
    
    List<Task> findByUserAndCompleted(User user,boolean completed,Sort sort);
    
    List<Task> findByUserAndTitleContainingIgnoreCaseOrUserAndDescriptionContainingIgnoreCase(User user1, String titleKeyword, User user2, String descKeyword);
    
    Page<Task> findByUserAndCategoryId(User user, Long categoryId, Pageable pageable);

    Page<Task> findByUserAndCategoryIdAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
        User user, Long categoryId, String titleKeyword, String descKeyword, Pageable pageable);


    }

