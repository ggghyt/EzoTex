package com.ezotex.store.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezotex.store.service.InventoryService;
import com.ezotex.store.service.StoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/store/*")
public class InventoryController {

	private final StoreService service;
	
	@GetMapping("test")
	public String test(Model model) {
		model.addAttribute("list", service.list());
		return "store/agGridTest";
	}

	/**
	 * ========================================= 
	 * 입고관련 컨트롤
	 * =========================================
	 */

	// 자재/제품 입고 등록 페이지
	@GetMapping("insertStore")
	public String storeInsert(Model model) {
		model.addAttribute("list",service.list());
		return "store/insertStore";
	}

	// 반품 입고 등록 페이지
	@GetMapping("insertReturnStore")
	public String storeList() {
		return "store/insertReturnStore";
	}

	// 입고 문서 관리 페이지
	@GetMapping("docManagement")
	public String docManagement() {
		return "store/docManagement";
	}

	/**
	 * ========================================= 
	 * 자재관련 컨트롤
	 * =========================================
	 */

	// 재고 조회 페이지
	@GetMapping("productList")
	public String productList() {
		return "store/productList";
	}
	
	// 재고 조정 페이지
	@GetMapping("productManagement")
	public String productManagement() {
		return "store/productManagement";
	}
		
	// 실시간 재고 모니터링 페이지
	@GetMapping("monitoring")
	public String monitoring() {
		return "store/monitoring";
	}
		
	// 불량처리 조회 페이지
	@GetMapping("errorList")
	public String errorList() {
		return "store/errorList";
	}

}
