package com.ezotex.standard.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezotex.standard.service.impl.StandardServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/standard/*")
public class StandardController {

	final StandardServiceImpl service;
	
	// 제품 조회 페이지
	@GetMapping("/productInfo")
	public String productInfo() {
		return "/standard/productInfo";
	}
	
	// 제품 전체 조회
}
