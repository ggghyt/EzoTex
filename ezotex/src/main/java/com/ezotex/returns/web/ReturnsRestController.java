package com.ezotex.returns.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.returns.dto.DeliveryDetailsReturnsDTO;
import com.ezotex.returns.service.impl.ReturnsServiceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/returns/*")
public class ReturnsRestController {

	private final ReturnsServiceImpl service;
	
	@GetMapping("/returnsProduct") 
	public List<DeliveryDetailsReturnsDTO> deliveryCode(@RequestParam(name = "deliveryCode") String deliveryCode){
		return service.getDeliProduct(deliveryCode);
	}
}
