package com.example.portfolio.controller;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.portfolio.entity.Category;
import com.example.portfolio.entity.Task;
import com.example.portfolio.entity.User;
import com.example.portfolio.service.CategoryService;
import com.example.portfolio.service.TaskService;
import com.example.portfolio.service.UserService;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private CategoryService categoryService;

    // タスク一覧表示
    @GetMapping("/tasks")
    public String showTaskList(@RequestParam(defaultValue = "asc") String sort,
                               @RequestParam(defaultValue = "deadline") String sortBy,
                               @RequestParam(required = false) String status,
                               @RequestParam(required = false) String keyword,
                               @RequestParam(required = false) Long categoryId,
                               @RequestParam(defaultValue = "0") int page,
                               Model model, Principal principal) {

        Optional<User> userOpt = userService.findByEmail(principal.getName());

        if (userOpt.isEmpty()) {
            model.addAttribute("error", "未登録のユーザーです。");
            return "login";
        }

        User user = userOpt.get();

        Sort sortOption = "desc".equalsIgnoreCase(sort)
                ? Sort.by(Sort.Direction.DESC, sortBy)
                : Sort.by(Sort.Direction.ASC, sortBy);

        Pageable pageable = PageRequest.of(page, 5, sortOption);
        Page<Task> taskPage;

        // ------------------------
        // フィルタ条件ごとの分岐
        // ------------------------

        // キーワードあり
        if (keyword != null && !keyword.isEmpty()) {
            // カテゴリ + ステータス + キーワード
            if (categoryId != null) {
                if ("completed".equalsIgnoreCase(status)) {
                    taskPage = taskService.searchTasksByCategoryAndStatus(user, categoryId, keyword, true, pageable);
                } else if ("incomplete".equalsIgnoreCase(status)) {
                    taskPage = taskService.searchTasksByCategoryAndStatus(user, categoryId, keyword, false, pageable);
                } else {
                    taskPage = taskService.searchTasksByCategory(user, categoryId, keyword, pageable);
                }
            }
            // ステータス + キーワードのみ
            else {
                if ("completed".equalsIgnoreCase(status)) {
                    taskPage = taskService.searchTasksWithStatus(user, keyword, true, pageable);
                } else if ("incomplete".equalsIgnoreCase(status)) {
                    taskPage = taskService.searchTasksWithStatus(user, keyword, false, pageable);
                } else {
                    taskPage = taskService.searchTasks(user, keyword, pageable);
                }
            }
        }

        // キーワードなし
        else {
            // カテゴリ + ステータス
            if (categoryId != null) {
                if ("completed".equalsIgnoreCase(status)) {
                    taskPage = taskService.getTasksByCategoryAndStatus(user, categoryId, true, pageable);
                } else if ("incomplete".equalsIgnoreCase(status)) {
                    taskPage = taskService.getTasksByCategoryAndStatus(user, categoryId, false, pageable);
                } else {
                    taskPage = taskService.getTasksByUserAndCategory(user, categoryId, pageable);
                }
            }
            // ステータスのみ
            else {
                if ("completed".equalsIgnoreCase(status)) {
                    taskPage = taskService.getTasksByUserAndStatus(user, true, pageable);
                } else if ("incomplete".equalsIgnoreCase(status)) {
                    taskPage = taskService.getTasksByUserAndStatus(user, false, pageable);
                } else {
                    taskPage = taskService.getTasksByUser(user, pageable);
                }
            }
        }

        // ------------------------
        // モデルへの追加
        // ------------------------

        model.addAttribute("tasks", taskPage);
        model.addAttribute("filteredTasks", taskPage.getContent());
        model.addAttribute("sort", sort);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("status", status);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("loginName", user);

        return "task-list";
    }


    // タスク登録フォーム表示
    @GetMapping("/new")
    public String showTaskForm(Model model,Principal principal) {
    	 User user = userService.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("task", new Task());
        model.addAttribute("categories", categoryService.findForUser(user));
        
        return "task-form";
    }


    // タスク登録処理
    @PostMapping("/new")
    public String createTask(@Valid @ModelAttribute Task task,
    		 					BindingResult bindingResult,
    		 					@RequestParam("categoryId")Long categoryId,
    							Principal principal,
    							Model model) {
    	 User user = userService.findByEmail(principal.getName()).orElseThrow();
    	 
    	if(bindingResult.hasErrors()) {
    		   // カテゴリ一覧を再セット（バリデーションエラー時に必要）
    		model.addAttribute("categories", categoryService.findForUser(user));
    		return "task-form";
    	}
    	
        Optional<User> userOpt = userService.findByEmail(principal.getName());
        Optional<Category> categoryOpt = categoryService.findById(categoryId);
        
        if (userOpt.isPresent() && categoryOpt.isPresent()) {
            task.setUser(userOpt.get());
            task.setCategory(categoryOpt.get()); 
            taskService.save(task);
            return "redirect:/tasks";
        } else {
            return "redirect:/login";
        }
    }
    //　タスク編集画面
    @GetMapping("/tasks/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, Principal principal) {

    	Optional<User> userOpt = userService.findByEmail(principal.getName());
        Optional<Task> taskOpt = taskService.findById(id);
   	 	User user = userService.findByEmail(principal.getName()).orElseThrow();

        if (userOpt.isPresent() && taskOpt.isPresent()) {
            User loginUser = userOpt.get();
            Task task = taskOpt.get();

            if (task.getUser().getId().equals(loginUser.getId())) {
                model.addAttribute("task", task);
        		model.addAttribute("categories", categoryService.findForUser(user));
                return "task-edit";
            }
        }
        return "redirect:/tasks";
    }
    
 //　タスク編集後
    @PostMapping("/tasks/edit/{id}")
    public String updateTask(@PathVariable Long id,
    						@Valid @ModelAttribute Task updatedTask,
    						BindingResult bindingResult,
    						@RequestParam("categoryId") Long categoryId,
    						Principal principal,
    						Model model) {
    	
   	 User user = userService.findByEmail(principal.getName()).orElseThrow();

    	if(bindingResult.hasErrors()) {
    		model.addAttribute("categories", categoryService.findForUser(user));
    		model.addAttribute("task", updatedTask);
    		return "task-edit";
    	}
    		
        Optional<User> userOpt = userService.findByEmail(principal.getName());
        Optional<Task> taskOpt = taskService.findById(id);
        Optional<Category> categoryOpt = categoryService.findById(categoryId);

        if (userOpt.isPresent() && taskOpt.isPresent()) {
            Task existingTask = taskOpt.get();

            // セキュリティチェック：タスクの所有者とログインユーザーが一致するか
            if (existingTask.getUser().getId().equals(userOpt.get().getId())) {
                // 更新内容を反映
                existingTask.setTitle(updatedTask.getTitle());
                existingTask.setDescription(updatedTask.getDescription());
                existingTask.setDeadline(updatedTask.getDeadline());
                existingTask.setCompleted(updatedTask.isCompleted());
                existingTask.setCategory(categoryOpt.get()); 
                taskService.save(existingTask);
                
                return "redirect:/tasks";
            }
            
        }
        	
        	return "redirect:/login";
        	
        
    }


 // タスク削除処理
    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, Principal principal) {
        Optional<User> userOpt = userService.findByEmail(principal.getName());
        Optional<Task> taskOpt = taskService.findById(id);

        if (userOpt.isPresent() && taskOpt.isPresent()) {
            Task task = taskOpt.get();

            // セキュリティ: ログインユーザーが所有者か確認
            if (task.getUser().getId().equals(userOpt.get().getId())) {
                taskService.deleteTask(task); // ← 実行
            }
        }

        return "redirect:/tasks";
    }
   
  //完了ボタン推したらその場でON,OF切り替わる
    @PostMapping("/tasks/update-status")
    public String updateTaskStatus(@RequestParam Long id,
                                   @RequestParam(required = false) String completed,
                                   Principal principal) {
    	
        Optional<User> userOpt = userService.findByEmail(principal.getName());
        Optional<Task> taskOpt = taskService.findById(id);

        if (userOpt.isPresent() && taskOpt.isPresent()) {
            User loginUser = userOpt.get();
            Task task = taskOpt.get();

            if (task.getUser().getId().equals(loginUser.getId())) {
                // チェックボックスがオンなら"on"、オフならnullになる
                task.setCompleted(completed != null);
                
                // ✅ Categoryの再取得（デタッチ対策）
                if (task.getCategory() != null) {
                    Long categoryId = task.getCategory().getId();
                    Optional<Category> categoryOpt = categoryService.findById(categoryId);
                    categoryOpt.ifPresent(task::setCategory);
                }
                taskService.save(task);
            }
        }

        return "redirect:/tasks";
    }


}
