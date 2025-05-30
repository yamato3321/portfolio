package com.example.portfolio.controller;

import java.security.Principal;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.portfolio.entity.Category;
import com.example.portfolio.entity.User;
import com.example.portfolio.service.CategoryService;
import com.example.portfolio.service.UserService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UserService userService;

    // カテゴリ一覧表示 + 新規フォーム
    @GetMapping
    public String listCategories(Model model,Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.findForUser(user));
        return "category-list";
    }

    // カテゴリ追加
    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute Category category,
                              BindingResult bindingResult,
                              Model model,
                              Principal principal) {
    	User user = userService.findByEmail(principal.getName()).orElseThrow();
    	 
        if (bindingResult.hasErrors()) {
        	 
            model.addAttribute("categories",categoryService.findForUser(user));
            return "category-list";
        }
        
        category.setUser(user); 
        categoryService.save(category);
        return "redirect:/categories";
    }

    // 編集画面（同じテンプレートに再描画）
    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model,Principal principal) {
        Category category = categoryService.findById(id).orElseThrow();
        
        if (category.isShared()) {
            model.addAttribute("deleteError", "共通カテゴリは編集できません。");
            return "redirect:/categories";
        }
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        
        model.addAttribute("category", category);
        model.addAttribute("categories",categoryService.findForUser(user));
        return "category-list";
    }

    // 編集処理（/update/{id} にPOST）
    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable Long id,
                                 @Valid @ModelAttribute Category category,
                                 BindingResult bindingResult,
                                 Model model,
                                 Principal principal) {
    	
    	User user = userService.findByEmail(principal.getName()).orElseThrow();
    	
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories",categoryService.findForUser(user));
            return "category-list";
        }
        category.setId(id); // 明示的にIDを維持
        category.setUser(user);
        categoryService.save(category);
        return "redirect:/categories";
    }

    // 削除処理（Taskに紐づくカテゴリ・共通カテゴリは削除不可）
    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, Model model,Principal principal) {
    	
    	User user = userService.findByEmail(principal.getName()).orElseThrow();
    	Category category = categoryService.findById(id).orElseThrow();
    	
        if (category.isShared()) {
            model.addAttribute("deleteError", "共通カテゴリは削除できません。");
            model.addAttribute("categories", categoryService.findForUser(user));
            model.addAttribute("category", new Category());
            return "category-list";
        }
    	
        if (categoryService.hasTasksLinked(id)) {
            model.addAttribute("deleteError", "このカテゴリはタスクに紐づいているため削除できません。");
            model.addAttribute("categories", categoryService.findForUser(user));
            model.addAttribute("category", new Category());
            return "category-list";
        }

        categoryService.deleteById(id);
        return "redirect:/categories";
    }
}
