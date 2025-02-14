package com.ezotex.order.web;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

}
