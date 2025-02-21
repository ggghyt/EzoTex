package com.ezotex.delivery.web;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/delivery/*")
public class DeliveryController {
	
	
	//납품 관리 페이지(제조업체 사용)
	@GetMapping("DeliveryManagement")
	public String deliveryManagement() {
		
		return "delivery/DeliveryManagement";
	}
	
	//납품 관리 페이지(공급업체 사용)
	@GetMapping("SupplierManagement")
	public String SupplyManagement() {
		
		return "delivery/SupplierDeliveryManagement";
	}
	
}
