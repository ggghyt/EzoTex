package com.ezotex.standard.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.standard.dto.ProductOptionDTO;
import com.ezotex.standard.service.impl.ProductServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/*")
public class ProductRestController {
	
	private final ProductServiceImpl prdService;
	
	// 선택한 제품의 옵션 목록
	@GetMapping("option/{productCode}")
	public List<ProductOptionDTO> optionList(@PathVariable String productCode) {
		List<ProductOptionDTO> options = prdService.listOption(productCode);
		return options;
	}
	
	// 카테고리-대분류 전체 목록
	@GetMapping("category")
	public String[]lclasList() {
		String[] categories = prdService.listLclas();
		return categories;
	}
	
	// 카테고리-대분류-소분류 목록
	@GetMapping("category/{lclas}")
	public String[] sclasList(@PathVariable String lclas) {
		String[] categories = prdService.listSclasByLclas(lclas);
		return categories;
	}
}
