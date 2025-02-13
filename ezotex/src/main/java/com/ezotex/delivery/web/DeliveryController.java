package com.ezotex.delivery.web;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezotex.comm.GridUtil;
import com.ezotex.delivery.service.DeliveryService;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/delivery/*")
public class DeliveryController {
	

	//납품 관리
	private DeliveryService service;
	
	
	//납품 관리 페이지
	@GetMapping("DeliveryManagement")
	public String deliveryManagement(Model model) {
		
		//model.addAttribute("list", service.getList());
		return "delivery/DeliveryManagement";
	}
	

	
}
