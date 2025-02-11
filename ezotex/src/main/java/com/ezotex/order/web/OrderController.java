package com.ezotex.order.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezotex.order.dto.OrderDTO;
import com.ezotex.order.service.impl.OrderServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/order/*")
public class OrderController {
	
	final OrderServiceImpl service;
	
	@GetMapping("/OrderDetailList")
	public void OrderDetailList(Model model) {
		List<OrderDTO> orderList = service.getOrderList();
		model.addAttribute("getOrderList",orderList);
	}
	@GetMapping("/OrderManagement")
	public void OrderManagement(Model model) {		
		List<OrderDTO> productList = service.getProductList();
		model.addAttribute("getProductList",productList);
	}
	@GetMapping("/OrderDetailListModal")
	public void OrderDetailListModal(Model model) {
		
	}
//	@GetMapping("/OrderManagement")
//	public String OrderManagement(Model model) {
//		List<OrderVO> orderList = service.getOrderList();
//		model.addAttribute("orderList",orderList);
//		return "order/OrderManagement";
//	}
	@GetMapping("/ToastTest")
	public void ToastTest(Model model) {
		
	}

}
