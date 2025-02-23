package com.ezotex.order.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezotex.order.dto.OrderDTO;
import com.ezotex.order.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@org.springframework.web.bind.annotation.RestController
@AllArgsConstructor
@RequestMapping("/order/*")
public class RestController {

	@Autowired
	private ObjectMapper objectMapper;

	private final OrderServiceImpl service;

	@GetMapping("/ProductOption")
	public List<OrderDTO> ProductCode(@RequestParam(name = "productCode") String productCode) {
		return service.getProductOption(productCode);
	}

	// 주문 등록
	@PostMapping("/insertOrder")
	public OrderDTO InsertOrder(@RequestBody OrderDTO order) {
		System.out.println("---------------------------------------------------");
		System.out.println(order);
		return service.insertOrder(order);
	}

	// 제품 등록
	/*
	 * @PostMapping("/insertProductOrder") public boolean
	 * InsertProductOrder(@RequestBody List<OrderDTO> productOrderList) {
	 * log.info(productOrderList.toString());
	 * System.out.println("product product product" + productOrderList); return
	 * service.insertProductOrder(productOrderList); }
	 */
	// 제품 등록
	@PostMapping("/insertProductOrder")
	public boolean InsertProductOrder(@RequestBody Map<String, Object> productOrderList) {

		List<OrderDTO> odto = objectMapper.convertValue(productOrderList.get("option"),
				new TypeReference<List<OrderDTO>>() {
				});

		productOrderList.put("option", odto);
		return service.insertProductOrder(productOrderList);
	}
	
	@GetMapping("/orderProductList")
	public List<OrderDTO> getOrderProduct(@RequestParam String productOrderCode) {
		System.out.println("컨트롤러 통신확인");
	    return service.getOrderProduct(productOrderCode);
	}
	
	@PostMapping("/orderDelete")
	public ResponseEntity<String> orderDelete(@RequestBody OrderDTO order) {
		boolean productDel = service.deleteOrderProduct(order.getProductOrderCode()); // 삭제 수행
	    boolean orderDel = service.deleteOrderList(order.getProductOrderCode()); // 삭제 수행
	    if (orderDel || productDel) {
	        return ResponseEntity.ok("삭제 성공");
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 실패");
	    }
	}
}
