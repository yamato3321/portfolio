package com.example.portfolio.service;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.portfolio.dto.SignUpForm;
import com.example.portfolio.entity.User;
import com.example.portfolio.repository.UserRepository;

@Service
public class UserService {
	 private final PasswordEncoder passwordEncoder;
	 private final UserRepository userRepository;
	

	  public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
	    this.passwordEncoder = passwordEncoder;
	    this.userRepository = userRepository;
    }
	  
	public String hashPassword(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}
	
	public boolean checkPassword(String rawPassword,String hashedPassword) {
		return passwordEncoder.matches(rawPassword, hashedPassword);
	}
	
	public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
	
	public User registerUser(SignUpForm form) {
	    String hashedPassword = passwordEncoder.encode(form.getPassword());
	    User user = new User();
	    user.setEmail(form.getEmail());
	    user.setPassword(hashedPassword);
	    user.setName(form.getName());
	    return userRepository.save(user);
	}
	
	
	public void updateUserProfile(String currentEmail, String newName) {
	    User user = userRepository.findByEmail(currentEmail).orElseThrow();
	    user.setName(newName);
	    userRepository.save(user);
	}
	
	public void updateUserPassword(String email,String currentPassword,String newPassword) {
		User user = userRepository.findByEmail(email).orElseThrow();
		
		if(!passwordEncoder.matches(currentPassword, user.getPassword())) {
			throw new IllegalArgumentException("現在のパスワードが正しくありません");
		}
		
		
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

}
