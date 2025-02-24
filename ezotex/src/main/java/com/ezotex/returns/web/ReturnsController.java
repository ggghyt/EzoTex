package com.ezotex.returns.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezotex.returns.dto.DeliveryDetailsReturnsDTO;
import com.ezotex.returns.dto.DeliveryReturnsDTO;
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

	@GetMapping("/returnsManagement")
	public void DeliveryList(Model model) {
		List<DeliveryReturnsDTO> deliveryList = service.getDeliveryList();
		model.addAttribute("getDeliveryList", deliveryList);
		String name = (String) session.getAttribute("name");
		model.addAttribute("name",name);
	}	
}
