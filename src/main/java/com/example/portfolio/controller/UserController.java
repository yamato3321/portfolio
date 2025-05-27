package com.example.portfolio.controller;

import java.security.Principal;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.portfolio.dto.EditProfileForm;
import com.example.portfolio.entity.User;
import com.example.portfolio.service.UserService;

@Controller
@RequestMapping("/profile")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String showProfile(Model model,Principal principal) {
		User user = userService.findByEmail(principal.getName()).orElseThrow();
		EditProfileForm form = new EditProfileForm();
		form.setName(user.getName());
		form.setEmail(user.getEmail());
		model.addAttribute("editProfileForm", form);
		return "profile";
	}
	
	@PostMapping
	public String updateProfile(@Valid @ModelAttribute EditProfileForm form,
								BindingResult bindingResult,
								Principal principal,
								Model model) {
		
		if(bindingResult.hasErrors()) {
			return "profile";
		}
		
		try {
			userService.updateUserProfile(principal.getName(), form.getName());
			userService.updateUserPassword(principal.getName(), form.getCurrentPassword(), form.getNewPassword());
		}catch(IllegalArgumentException e){
			model.addAttribute("passwordError", e.getMessage());
	        return "profile";
		}
        
		return "redirect:/profile?updated";
	}
	
}