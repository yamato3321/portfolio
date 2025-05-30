package com.example.portfolio.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
//	@Autowired
//	private UserRepository userRepository;
//	
//	@Autowired
//	private UserService userService;
	
	@GetMapping("/")
	public String showWelcomePage(Principal principal) {
	    // ログイン済みなら principal に値が入る
	    if (principal != null) {
	        return "redirect:/tasks";
	    }
	    return "home"; // 未ログインなら通常通り home.html を表示
	}

	
// SignupControllerに移動	
//	@GetMapping("/signup")
//	public String singup() {
//		return "signup";
//	}
	
	
// SignupControllerに移動
//	@PostMapping("/signup")
//	public String registerUser(
//			@RequestParam("email") String email,
//			@RequestParam("password") String password,
//			@RequestParam("confirmPassword") String confirmPassword,
//			Model model) {
//		
//		//簡単なバリデーション
//		if(!password.equals(confirmPassword)) {
//			model.addAttribute("error", "Passwords do not match!");
//			model.addAttribute("email", email);
//			return "signup";
//		}
//		
//		//パスワードハッシュ化して保存
//		String hashedPassword = userService.hashPassword(password);
//		//保存処理
//		User user = new User();
//		user.setEmail(email);
//		user.setPassword(hashedPassword);
//		userRepository.save(user);
//
//		
//		model.addAttribute("message", "User registration successful!");
//		return "signup-success";
//	}
	
	@GetMapping("/home")
	public String showHome(Model model) {
		
//	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////	    System.out.println("認証クラス: " + auth.getClass());
////	    System.out.println("認証 principal: " + auth.getPrincipal());
////	    System.out.println("認証 name: " + auth.getName());
//	    
//	    Object principal = auth.getPrincipal();
//	    if (principal instanceof UserDetails userDetails) {
//	        model.addAttribute("email", userDetails.getUsername());
//	    } else {
//	        model.addAttribute("email", "ゲスト");
//	    }
		
	    return "home"; // templates/home.html を返す
	}
	
//	意図的に500発生!!
//	@GetMapping("/test-error")
//	public String testError() {
//	    throw new RuntimeException("意図的なエラー");
//	}


}
