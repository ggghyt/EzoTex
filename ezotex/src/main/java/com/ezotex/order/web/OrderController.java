package com.ezotex.order.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezotex.order.dto.OrderDTO;
import com.ezotex.order.service.impl.OrderServiceImpl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/order/*")
public class OrderController {
	
	final OrderServiceImpl service;
	final HttpSession session;
	
	@GetMapping("/OrderDetailList")
	public void OrderDetailList(Model model) {
		List<OrderDTO> orderList = service.getOrderList();
		model.addAttribute("getOrderList",orderList);
	}
	@GetMapping("/OrderManagement")
	public void OrderManagement(Model model) {		
		List<OrderDTO> productList = service.getProductList();
		model.addAttribute("getProductList",productList);
		List<OrderDTO> companyList = service.getCompanyList();
		model.addAttribute("getCompanyList",companyList);
		String name = (String) session.getAttribute("name");
		model.addAttribute("name",name);
		String code = (String) session.getAttribute("code");
		model.addAttribute("code",code);
		String orderCode = service.getOrderCode();
		model.addAttribute("getOrderCode",orderCode);
		System.out.println(orderCode);
	}
	
	
	
}
