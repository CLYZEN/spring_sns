package com.sns.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 로그인에 대한 설정
		http.authorizeHttpRequests(authorize -> authorize // 1. 페이지 접근에 대한 설정
				// 모든 사용자가 로그인(인증) 없이 접근할 수 있도록 설정
				.requestMatchers("/css/**","/js/**","/img/**", "/images/**","/fonts/**","/lib/**","/html/**").permitAll()
				.requestMatchers("/","/members/**","/item/**","/contact/**").permitAll()
				.requestMatchers("/favicon.ico", "/error").permitAll()
				// admin 으로 시작하는 경로는 관리자만 접근 가능하도록 설정
				.requestMatchers("/admin/**").hasRole("ADMIN")
				// 그 외의 페이지는 모두 로그인(인증 필요)
				.anyRequest().authenticated() 
				)
		.formLogin(formLogin -> formLogin // 2. 로그인 관련 설정
				// 로그인 페이지 경로 설정
				.loginPage("/members/login")
				// 로그인 성공 시 이동할 페이지
				.defaultSuccessUrl("/main")
				// 로그인 시 id로 사용할 파라미터 이름
				.usernameParameter("email")
				// 로그인 실패 시 이동할 URL
				.failureUrl("/members/login/error")
				//.permitAll()
				)
		.logout(logout -> logout // 3. 로그아웃에 관한 설정
				// 로그아웃 시 이동할 URL
				.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
				// 로그아웃 성공 시 이동
				.logoutSuccessUrl("/")
				//.permitAll()
				)
		.exceptionHandling(handling -> handling // 4. 인증되지 않은 사용자가 리소스에 접근했을 때 설정 (ex. 로그인안했는데 order, cart 에 접근)
				.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
				)
		.rememberMe(Customizer.withDefaults());
				
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
