package com.ezotex.standard.web;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezotex.comm.GridUtil;
import com.ezotex.comm.dto.PagingDTO;
import com.ezotex.standard.dto.ProductCategoryDTO;
import com.ezotex.standard.dto.ProductListInfoDTO;
import com.ezotex.standard.service.impl.StandardServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import groovyjarjarantlr4.v4.runtime.misc.ParseCancellationException;
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
	public Map<String, Object> productListAll() {
		ProductListInfoDTO productListInfoDTO = new ProductListInfoDTO();
		return GridUtil.grid(1, service.getCount(productListInfoDTO), service.productListAll(productListInfoDTO));
	}
	
	// 제품 선택 조회
	@ResponseBody
	@GetMapping("/productListSearch")
	public Map<String, Object> productListSearch(@RequestParam(name="productCode") String productCode, 
			                                     @RequestParam(name="productName") String productName, 
			                                     @RequestParam(name="productType") String productType, 
			                                     @RequestParam(name="lclas") String lclas, 
			                                     @RequestParam(name="sclas") String sclas, 
			                                     @RequestParam(name="minPrice") String minPrice, 
			                                     @RequestParam(name="maxPrice") String maxPrice) {
		ProductListInfoDTO productListInfoDTO = new ProductListInfoDTO();
		productListInfoDTO.setProductCode(productCode);
		productListInfoDTO.setProductName(productName);
		productListInfoDTO.setProductType(productType);
		productListInfoDTO.setLclas(lclas);
		productListInfoDTO.setSclas(sclas);
		int num;
		if (minPrice == "") {
			num = 0; 
		} else {
			num = Integer.parseInt(minPrice);
		}
		productListInfoDTO.setMinPrice(num);
		if (maxPrice == "") {
			num = 999999999;
		} else {
			num = Integer.parseInt(maxPrice);
		}
		productListInfoDTO.setMaxPrice(num);
		return GridUtil.grid(1, service.getCount(productListInfoDTO), service.productListAll(productListInfoDTO));
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
