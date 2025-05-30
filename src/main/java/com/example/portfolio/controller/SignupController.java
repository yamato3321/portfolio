package com.example.portfolio.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.portfolio.dto.SignUpForm;
import com.example.portfolio.service.UserService;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    // サインアップ画面の表示
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "signup";
    }


    // ユーザー登録処理
    @PostMapping("/signup")
    public String registerUser(@Valid @ModelAttribute("signUpForm") SignUpForm form,
    							BindingResult bindingResult,
                               Model model) {


        if (!form.getPassword().equals(form.getConfirmPassword())) {
        	bindingResult.rejectValue("confirmPassword","error.confirmPassword","パスワードが一致しません");
        }
        
        if(bindingResult.hasErrors()) {
        	return "signup";
        }

        // 保存処理
        userService.registerUser(form);
        return "redirect:/login?signup";
    }
}
