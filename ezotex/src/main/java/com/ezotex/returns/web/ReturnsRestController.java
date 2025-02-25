package com.ezotex.returns.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.returns.dto.DeliveryDetailsReturnsDTO;
import com.ezotex.returns.dto.ReturnsDTO;
import com.ezotex.returns.dto.ReturnsProductDTO;
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
	
	// 반품 헤더 등록
	@PostMapping("/insertReturn")
	public ReturnsDTO insertReturn(@RequestBody ReturnsDTO returnData) {
		System.out.println(returnData);
		return service.insertReturn(returnData);
	}
	
	// 반품 제품 등록
	@PostMapping("/insertProductReturn")
	public List<ReturnsProductDTO> insertProductReturn(@RequestBody List<ReturnsProductDTO> returnProductData) {
		System.out.println("returnProductData 데이터 확인"+returnProductData);
		return service.insertProductReturn(returnProductData);
	}
}
