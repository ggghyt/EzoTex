package com.ezotex.supply.web;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		model.addAttribute("prdLclasList", prdService.listLclas("PT02")); // 제품 대분류 기본 출력
		return "supply/supplyPlan";
	}
	

	@GetMapping("listPlan")
	public String supplyPlanList(Model model) {
		model.addAttribute("thisYear", LocalDate.now().getYear());
		return "supply/supplyPlanList";
	}
	
	@GetMapping("mrpList")
	public String mrpList(Model model) {
		model.addAttribute("thisYear", LocalDate.now().getYear());
		return "supply/mrpList";
	}
	
	@GetMapping("mtrOrder")
	public String materialOrder(@RequestParam Map<String, String> plan, Model model) {
		model.addAttribute("prdLclasList", prdService.listLclas("PT01")); // 자재 대분류 기본 출력
		model.addAttribute("selectedPlan", plan);
		return "supply/materialOrder";
	}
	
	@GetMapping("mtrPlan")
	public String materialOrderPlan(Model model) {
		model.addAttribute("prdLclasList", prdService.listLclas("PT01")); // 자재 대분류 기본 출력
		return "supply/materialOrderPlan";
	}
	
	@GetMapping("mPlanList")
	public String materialOrderPlanList(Model model) {
		return "supply/materialOrderPlanList";
	}
	
	@GetMapping("mOrderList")
	public String materialOrderList(Model model) {
		return "supply/materialOrderList";
	}
		
}
