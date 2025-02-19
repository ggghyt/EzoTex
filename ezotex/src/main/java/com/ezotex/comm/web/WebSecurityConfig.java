package com.ezotex.comm.web;

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

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				
				.requestMatchers("/loginProc", "/css/**", "/fonts/**", "/images/**", "/js/**", "/pages/**", "/partials/**", "/scss/**", "/vendors/**", "/login/**", "/img/**").permitAll()
				.requestMatchers("/*").hasAnyRole("EMP", "SUPPLY")
				.requestMatchers("/*").hasRole("SUPPLY")
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login/main")
				.loginProcessingUrl("/loginProc")
				.usernameParameter("id")
				.successHandler(authenticationSuccessHandler())
				.failureHandler(authenticationFailureHandler())
				.permitAll()
			)
			.rememberMe(r -> r.tokenValiditySeconds(86400).key("uniqueAndSecret")
			)
			.logout((logout) -> logout.permitAll().deleteCookies("remember-me", "JSESSIONID"))
			//.csrf(csrf -> csrf.disable())
			;
		
		http.exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHander()));
		return http.build();
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