package com.example.portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

//  リポジトリーはデータベースとのやりとりの部分だから設計として不適切
//	@Autowired
//	private UserRepository userRepository;
	
//	@Autowired
//	private UserService userService;

	
	//　設計的にサービス層でパスワード照合するのが適切	
//		@Autowired
//		private BCryptPasswordEncoder passwordEncoder;

	
	//ログイン画面の表示
	@GetMapping("/login")
	public String showLoginForm() {
		return"login";
		
	}
	
//	@PostMapping("/login")
//	public String loginUser(@RequestParam String email,
//							@RequestParam String password,
//							Model model) {
//		
//		//Optional<User> userOpt = userRepository.findByEmailAndPassword(email,password);
//		Optional<User> userOpt = userService.findByEmail(email);
//		
//		if(userOpt.isPresent()) {
//			User user = userOpt.get();
//			//ログイン成功
//			//パスワード照合
////			if(passwordEncoder.matches(password,user.getPassword())) {
////				return "redirect:/home";
////			}
//			if (userService.checkPassword(password, user.getPassword())) {
//                return "redirect:/home";
//            }
//			
//		}
//		
//			//ログイン失敗
//			model.addAttribute("error","メールアドレスまたはパスワードが正しくありません。");
//			return"login";
//		
//	}
	
}
