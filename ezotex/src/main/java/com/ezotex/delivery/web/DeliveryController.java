package com.ezotex.delivery.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {
	
	@GetMapping("/regist")
	public String index() {
		return "delivery/DeliveryManagement";
	}
	//수정
	
}
