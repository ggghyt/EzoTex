package com.ezotex.order.web;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezotex.order.dto.OrderDTO;
import com.ezotex.order.service.impl.OrderServiceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@org.springframework.web.bind.annotation.RestController
@AllArgsConstructor
@RequestMapping("/order/*")
public class RestController {
	
	private final OrderServiceImpl service;
	
	@GetMapping("/ProductOption")
	public List<OrderDTO> ProductCode(@RequestParam(name="productCode") String productCode) {
		return service.getProductOption(productCode);
	}
	
	// 주문 등록
	@PostMapping("/insertOrder")
	public boolean InsertOrder(@RequestBody List<OrderDTO> order) {
		System.out.println("---------------------------------------------------");
		System.out.println(order);
		return service.insertOrder(order);
	}
	// 제품 등록
	@PostMapping("/insertProductOrder")
	public boolean InsertProductOrder(@RequestBody List<OrderDTO> product) {
		log.info(product.toString());
		System.out.println("product product product product product product product product product product product product product product product" + product);
		return service.insertProductOrder(product);
	}
	
}
