package com.ezotex.standard.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.standard.service.impl.ProductServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/*")
public class ProductRestController {
	
	private final ProductServiceImpl prdService;
	
	// 카테고리-대분류-소분류 목록
	@GetMapping("category/{lclas}")
	public String[] sclasList(@PathVariable String lclas) {
		String[] categories = prdService.listSclasByLclas(lclas);
		return categories;
	}
}
