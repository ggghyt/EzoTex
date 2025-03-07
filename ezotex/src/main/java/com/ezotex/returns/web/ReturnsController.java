package com.ezotex.returns.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezotex.returns.dto.DeliveryReturnsDTO;
import com.ezotex.returns.dto.ReturnsDTO;
import com.ezotex.returns.dto.ReturnsProductDTO;
import com.ezotex.returns.dto.SalesDTO;
import com.ezotex.returns.dto.changeDTO;
import com.ezotex.returns.service.impl.ReturnsServiceImpl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/returns/*")
public class ReturnsController {

	final ReturnsServiceImpl service;
	final HttpSession session;

	// 납품 내역 조회
	@GetMapping("/returnsManagement")
	public void DeliveryList(Model model) {
		List<DeliveryReturnsDTO> deliveryList = service.getDeliveryList();
		model.addAttribute("getDeliveryList", deliveryList);
		String name = (String) session.getAttribute("name");
		model.addAttribute("name", name);
	}

	// 반품 등록된것중 상태가 교환인것만 조회
	@GetMapping("/changeManagement")
	public void changeList(Model model) {
		List<changeDTO> changeList = service.getChangeList();
		model.addAttribute("getChangeList", changeList);
	}

	// 반품 내역 전체조회
	@GetMapping("/returnList")
	public void returnList(Model model) {
		List<ReturnsDTO> returnList = service.getReturnList();
		model.addAttribute("getReturnList", returnList);
		List<ReturnsProductDTO> returnProductList = service.getReturnProductList();
		model.addAttribute("getReturnProductList", returnProductList);
	}

	// 손실액 조회
	@GetMapping("/lossAmountList")
	public void lossAmountList(Model model) {
		List<ReturnsDTO> returnList = service.getReturnList();
		model.addAttribute("getReturnList", returnList);
		List<ReturnsProductDTO> returnProductList = service.getReturnProductList();
		model.addAttribute("getReturnProductList", returnProductList);
		// 일별 제품별 손실액
		List<ReturnsProductDTO> totalReturnProduct = service.getTotalReturnProduct();
		model.addAttribute("getTotalReturnProduct", totalReturnProduct);
		// 매출액 조회
		List<SalesDTO> salesList = service.getSalesList();
		model.addAttribute("getSalesList" , salesList);
		// 주문코드에 대한 매출액과 손실액 조회
		List<SalesDTO> salesAmount = service.getSalesAmount();
		model.addAttribute("getSalesAmount", salesAmount);
		// 일일 전체 매출 손실
		List<SalesDTO> daySalesLoss = service.getDaySalesLoss();
		model.addAttribute("getDaySalesLoss" , daySalesLoss);
	}

}
