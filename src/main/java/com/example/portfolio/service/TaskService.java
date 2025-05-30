package com.example.portfolio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.portfolio.entity.Task;
import com.example.portfolio.entity.User;
import com.example.portfolio.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    
    public Page<Task> getTasksByUser(User user,Pageable pageble){
    	return taskRepository.findByUser(user, pageble);
    }
    
    public Page<Task> searchTasks(User user,String keyword,Pageable pageable){
    	return taskRepository.findByUserAndTitleContainingIgnoreCaseOrUserAndDescriptionContainingIgnoreCase(user, keyword, user, keyword, pageable);
    }
    
    public Page<Task> searchTasksByCategoryAndStatus(User user, Long categoryId, String keyword, boolean completed, Pageable pageable) {
        return taskRepository.findByUserAndCategoryIdAndTitleContainingIgnoreCaseAndCompleted(
            user, categoryId, keyword, completed, pageable);
    }
    
    public Page<Task> searchTasksWithStatus(User user, String keyword, boolean completed, Pageable pageable) {
        return taskRepository.findByUserAndTitleContainingIgnoreCaseAndCompleted(user, keyword, completed, pageable);
    }

    public Page<Task> getTasksByCategoryAndStatus(User user, Long categoryId, boolean completed, Pageable pageable) {
        return taskRepository.findByUserAndCategoryIdAndCompleted(user, categoryId, completed, pageable);
    }

    public Page<Task> getTasksByUserAndStatus(User user, boolean completed, Pageable pageable) {
        return taskRepository.findByUserAndCompleted(user, completed, pageable);
    }

//    public Page<Task> searchTasksWithStatus(User user, String keyword, boolean completed, Pageable pageable) {
//        return taskRepository.findByUserAndCompletedAndTitleContainingIgnoreCaseOrUserAndCompletedAndDescriptionContainingIgnoreCase(
//            user, completed, keyword, user, completed, keyword, pageable);
//    }
//    
//    public Page<Task> getTasksByUserAndStatus(User user, boolean completed, Pageable pageable) {
//        return taskRepository.findByUserAndCompleted(user, completed, pageable);
//    }
    
    public Page<Task> getTasksByUserAndCategory(User user, Long categoryId, Pageable pageable) {
        return taskRepository.findByUserAndCategoryId(user, categoryId, pageable);
    }

    public Page<Task> searchTasksByCategory(User user, Long categoryId, String keyword, Pageable pageable) {
        return taskRepository.findByUserAndCategoryIdAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                user, categoryId, keyword, keyword, pageable);
    }


//    public List<Task> getTasksByUserSorted(User user,Sort sort) {
//        return taskRepository.findByUser(user, sort);
//    }
    
    public void createTaskForUser(Task task, User user) {
        task.setUser(user);
        taskRepository.save(task);
    }
    
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }
    
    public void save(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }
    
    public List<Task> getTasksByUserOrderByDeadlineAsc(User user){
    	return taskRepository.findByUserOrderByDeadlineAsc(user);
    }
    
    public List<Task> getTasksByUserOrderByDeadlineDesc(User user){
    	return taskRepository.findByUserOrderByDeadlineDesc(user);
    }
    
    public List<Task> getTasksByUserAndCompleted(User user, boolean completed,Sort sort){
    	return taskRepository.findByUserAndCompleted(user, completed, sort);
    }
    
    public List<Task> getTaskByUserAndTitleContainingIgnoreCaseOrUserAndDescriptionContainingIgnoreCase(User user1, String titleKeyword, User user2, String descKeyword){
    	return taskRepository.findByUserAndTitleContainingIgnoreCaseOrUserAndDescriptionContainingIgnoreCase(user1, titleKeyword, user2, descKeyword);
    }
}
