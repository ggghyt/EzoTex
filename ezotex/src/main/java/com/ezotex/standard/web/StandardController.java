package com.ezotex.standard.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezotex.standard.dto.ProductCategoryDTO;
import com.ezotex.standard.dto.ProductListInfoDTO;
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
	@ResponseBody
	@GetMapping("/productListAll")
	public List<ProductListInfoDTO> productListAll() {
		return service.productListAll();
	}
	
	// 제품 검색
	@ResponseBody
	@GetMapping("/productListSearch")
	public List<ProductListInfoDTO> productListSearch() {
		return service.productListAll();
	}
	
	// 카테고리 반환
	@ResponseBody
	@GetMapping("/categoryLclas")
	public List<ProductCategoryDTO> categoryLclas() {
		return service.categoryLclas();
	}
	
	@ResponseBody
	@GetMapping("/categorySclas")
	public List<ProductCategoryDTO> categorySclas(@RequestParam(name="lclas") String lclas) {
		return service.categorySclas(lclas);
	}
}
