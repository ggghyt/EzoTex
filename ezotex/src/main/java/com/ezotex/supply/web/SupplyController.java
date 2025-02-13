package com.ezotex.supply.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezotex.comm.GridUtil;
import com.ezotex.standard.dto.ProductDTO;
import com.ezotex.supply.service.impl.BomServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/supply/*")
public class SupplyController {
	
	private final BomServiceImpl service;
	
	@GetMapping("bom")
	public String bomPage() {
		return "/supply/BomManagement";
	}
	
	// bom 목록 데이터
	@ResponseBody
	@GetMapping("bomList")
	public Map<String, Object> bomList(ProductDTO product) { // 제품 검색 조건
		log.info(product.toString());
		int totalCnt = service.countBomProduct(product);
		Map<String, Object> map = GridUtil.grid(1, totalCnt, service.listBomProduct(product));
		log.info(map.toString());
		return map;
	}
	
}
