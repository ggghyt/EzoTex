package com.ezotex.supply.web;

import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezotex.standard.service.impl.ProductServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/supply/*")
public class SupplyController {
	
	private final ProductServiceImpl prdService;
	
	@GetMapping("bom")
	public String bomPage(Model model) {
		model.addAttribute("prdLclasList", prdService.listLclas("PT02")); // 제품 대분류 기본 출력
		model.addAttribute("mtrLclasList", prdService.listLclas("PT01")); // 자재 대분류 기본 출력
		return "supply/bomManagement";
	}
	
	@GetMapping("plan")
	public String supplyPlan(Model model) {		
		Calendar current = Calendar.getInstance();
		int currentYear = current.get(Calendar.YEAR);
		
		model.addAttribute("thisYear", currentYear); // 공급년도 기본값 올해부터
		model.addAttribute("seasonList", new String[] {"봄", "여름", "가을", "겨을"}); // 타임리프 계절 목록
		model.addAttribute("prdLclasList", prdService.listLclas("PT02")); // 제품 대분류 기본 출력
		return "supply/supplyPlan";
	}
	
	// 테스트...
	@GetMapping("test")
	public String storeInsert(Model model) {
		return "supply/insertStore2";
	}
		
}
