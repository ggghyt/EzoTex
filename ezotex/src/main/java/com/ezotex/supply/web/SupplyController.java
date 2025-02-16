package com.ezotex.supply.web;

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
		return "/supply/BomManagement";
	}
		
}
