package com.ezotex.order.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezotex.order.dto.OrderDTO;
import com.ezotex.order.service.impl.OrderServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order/*")
public class OrderController {
	
	final OrderServiceImpl service;
	
	@GetMapping("/OrderManagement")
	public void OrderManagement(Model model) {
		List<OrderDTO> orderList = service.getOrderList();
		model.addAttribute("orderList",orderList);
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
