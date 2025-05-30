package com.example.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SecurityConfig {
	   private final CustomUserDetailsService userDetailsService;

	    public SecurityConfig(CustomUserDetailsService userDetailsService) {
	        this.userDetailsService = userDetailsService;
	    }
	    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        	.authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
	    	    .requestMatchers("/","/login", "/signup", "/css/**").permitAll() // 非ログインでもOK
	    	    .anyRequest().authenticated()) // それ以外はログイン必須  
            //.csrf(csrf -> csrf.disable()) // CSRF保護も一旦無効
           // .formLogin(form -> form.disable()) // ログインフォームも無効
            .formLogin(form -> form
            	    .loginPage("/login") // 自作ログインページのURL
            	    .loginProcessingUrl("/login") // ログインPOST先URL
            	    .defaultSuccessUrl("/tasks", true) // ログイン成功後の遷移先
            	    .failureUrl("/login?error")
            	    .permitAll()
            	)
        	.logout(logout -> logout   
                .logoutUrl("/logout")  // ログアウト処理のURL
                .logoutSuccessUrl("/login?logout") // ログアウト後のリダイレクト先
                .invalidateHttpSession(true)  // セッション破棄
                .deleteCookies("JSESSIONID","remember-me")// セッションクッキーも削除
                ) 
        	.rememberMe(remenberMe -> remenberMe
        			.key("uniqueAndSecret")// 任意のシークレットキー　本番環境では気を
        			.tokenValiditySeconds(7 * 24 * 60 * 60)// 7日間
        			.userDetailsService(userDetailsService)
        			);
        
        return http.build();
    }
    
    private AccessDeniedHandler accessDeniedHandler() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
   
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    


}
