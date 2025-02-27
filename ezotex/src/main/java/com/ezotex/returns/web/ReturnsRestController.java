package com.ezotex.returns.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.returns.dto.ChangeOrderDTO;
import com.ezotex.returns.dto.DeliveryDetailsReturnsDTO;
import com.ezotex.returns.dto.ReturnsDTO;
import com.ezotex.returns.dto.ReturnsProductDTO;
import com.ezotex.returns.dto.changeDTO;
import com.ezotex.returns.service.impl.ReturnsServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/returns/*")
public class ReturnsRestController {
	
	@Autowired
	private ObjectMapper objectMapper;

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
	public boolean insertProductReturn(@RequestBody List<ReturnsProductDTO> returnProductData) {
		System.out.println("returnProductData 데이터 확인"+returnProductData);
		return service.insertProductReturn(returnProductData);
	}
	
	// 교환 제품 조회
	@GetMapping("/changeProductList")
	public List<changeDTO> returnCode(@RequestParam(name = "returnCode") String returnCode){
		return service.getChangeProductList(returnCode);
	}
	
	// 교환 주문 등록
	@PostMapping("/insertOrder")
	public ChangeOrderDTO InsertOrder(@RequestBody ChangeOrderDTO order) {
		System.out.println("---------------------------------------------------");
		System.out.println(order);
		return service.insertOrder(order);
	}
	
	// 교환 주문 제품 등록
	@PostMapping("/insertProductOrder")
	public boolean InsertProductOrder(@RequestBody Map<String, Object> productOrderList) {
		System.out.println("데이터 확인ㄱㄱㄱㄱ" + productOrderList);
		List<ChangeOrderDTO> odto = objectMapper.convertValue(productOrderList.get("option"),
				new TypeReference<List<ChangeOrderDTO>>() {
				});

		productOrderList.put("option", odto);
		return service.insertProductOrder(productOrderList);
	}
	
	@PostMapping("/showChange")
	public List<changeDTO> showChange(@RequestBody List<changeDTO> no){
		System.out.println("받을번호출력"+no);
		return service.showChange(no);
	}
}
