package com.example.portfolio.config;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.portfolio.entity.User;
import com.example.portfolio.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepository.findByEmail(email)
//            .map(CustomUserDetails::new)
//            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        System.out.println("ログイン試行 email: " + email);

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
//            System.out.println("ユーザーが見つかりませんでした");
            throw new UsernameNotFoundException("User not found");
        }
//        System.out.println("ユーザー取得成功: " + userOpt.get().getEmail());
//        System.out.println("ハッシュ化パスワード: " + userOpt.get().getPassword());
        return new CustomUserDetails(userOpt.get());
    }

}
