package com.ezotex.comm.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 적용
	        .csrf(csrf -> csrf
	            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // CSRF 토큰을 쿠키로 저장
	        )
			.authorizeHttpRequests((requests) -> requests
				
				.requestMatchers("/loginProc", "/css/**", "/fonts/**", "/images/**", "/js/**", "/pages/**", "/partials/**", "/scss/**", "/vendors/**", "/login/**", "/img/**").permitAll()
				//                로그인 페이지 지름 지정란거, 이 외의 항목들은 css너 js같은 실행하는데 사용되는 필수 파일들
				.requestMatchers("/*").hasAnyRole("EMP", "SUPPLY") // 역할(ROLE)을 부여하는 곳
				.requestMatchers("/*").hasRole("SUPPLY")
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login/main") // 로그인 페이지 경로
				.loginProcessingUrl("/loginProc") // 로그인 페이지에서 요청하는 폼의 action 경로
				.usernameParameter("id")	//form의 name속성중 id인 값을 가지고 옴
				.successHandler(authenticationSuccessHandler()) // 성공시 실행하는 핸들러
				.failureHandler(authenticationFailureHandler()) // 실패시 실행하는 핸들러
				.permitAll()	//위 요청은 모든 사용자가 요청 가능
			)
			.rememberMe(r -> r.tokenValiditySeconds(86400).key("uniqueAndSecret") // 세션 저장 (86400초 = 1일)
			)
			.logout((logout) -> logout.permitAll().deleteCookies("remember-me", "JSESSIONID")) // 로그이웃시 저장했던 세션 삭제
			//.csrf(csrf -> csrf.disable())
			;
		
		http.exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHander()));
		return http.build();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(List.of("http://localhost:8081")); // Vue.js 실행 주소
	    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    configuration.setAllowCredentials(true);
	    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-XSRF-TOKEN"));

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHander() {
		return new CustomAccessDeniedHandler();
	}
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomLoginSuccessHandler();
	}
	
	@Bean 
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new CustomLoginFailureHandler();
	}
}