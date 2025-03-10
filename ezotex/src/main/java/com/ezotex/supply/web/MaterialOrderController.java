package com.ezotex.supply.web;

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
@RequestMapping("/mtr/*")
public class MaterialOrderController {
	
	private final ProductServiceImpl prdService;
	
	@GetMapping("mtrOrder")
	public String materialOrder(@RequestParam Map<String, String> plan, Model model) {
		model.addAttribute("prdLclasList", prdService.listLclas("PT01")); // 자재 대분류 기본 출력
		if(!plan.isEmpty()) model.addAttribute("selectedPlan", plan);
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
