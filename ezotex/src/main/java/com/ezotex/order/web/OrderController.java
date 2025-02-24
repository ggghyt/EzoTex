package com.ezotex.order.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	// 주문 내역 조회
	@GetMapping("/OrderDetailList")
	public void OrderDetailList(Model model) {
		List<OrderDTO> orderList = service.getOrderList();
		model.addAttribute("getOrderList",orderList);
	}
	
	// 주문 관리 페이지
	@GetMapping("/OrderManagement")
	public void OrderManagement(Model model) {
		// 제품 조회
		List<OrderDTO> productList = service.getProductList();
		model.addAttribute("getProductList",productList);
		// 업체 조회
		List<OrderDTO> companyList = service.getCompanyList();
		model.addAttribute("getCompanyList",companyList);
		// 로그인된 사용자 세션
		String name = (String) session.getAttribute("name");
		model.addAttribute("name",name);
		// 로그인된 사용자 사원코드
		String code = (String) session.getAttribute("code");
		model.addAttribute("code",code);
		// 주문 코드
		String orderCode = service.getOrderCode();
		model.addAttribute("getOrderCode",orderCode);
		System.out.println(orderCode);
	}
	
	
	
}
