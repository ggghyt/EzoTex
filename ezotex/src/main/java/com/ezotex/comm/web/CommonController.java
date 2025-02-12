package com.ezotex.comm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/login/*")
public class CommonController {

    // 로그인 메인 페이지
	@GetMapping("/main")
	public String login() {
		return "/login/login.html";
	}

    // 회원가입 메인 페이지
	@GetMapping("/register_main")
	public String register_main() {
		return "/login/register_main.html";
	}

    // 공급업체 회원가입 페이지
	@GetMapping("/register_supply")
	public String register_supply() {
		return "/login/register_supply.html";
	}

	// 공급업체 회원가입 페이지
	@GetMapping("register_emp")
	public String register_emp() {
		return "/login/register_emp.html";
	}
}
