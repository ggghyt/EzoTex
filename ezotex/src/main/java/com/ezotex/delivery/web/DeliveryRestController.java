package com.ezotex.delivery.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.comm.GridUtil;
import com.ezotex.delivery.dto.Paging;
import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.OrderInfoDTO;
import com.ezotex.delivery.service.DeliveryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/delivery/*")
public class DeliveryRestController {
	

	//납품 관리
	private DeliveryService service;
	
	@GetMapping("orderList")
	public Map<String, Object> orderList(@RequestParam(name = "perPage", defaultValue = "1", required = false) int perPage,
			                        @RequestParam(name = "page", defaultValue = "1")int page,
			                        DeliveryRegistSearchDTO searchDTO
			                        ) throws JsonMappingException, JsonProcessingException {
		
		log.info(searchDTO.toString());
		Paging paging = new Paging();
		
		paging.setPageUnit(perPage);	//페이지당 최대 건수
		paging.setPage(page);			//현재 페이지

		// 페이징 조건
		searchDTO.setStart(paging.getFirst());	//시작
		searchDTO.setEnd(paging.getLast());		//끝번호
		
		paging.setTotalRecord(service.getCount(searchDTO));

		return GridUtil.grid(paging.getPage(), service.getCount(searchDTO), service.getList(searchDTO));
		
	}
	
	@GetMapping("deliveryList")
	public String deliveryList() {
		return "성공";
	}
	
	//주문정보
	@GetMapping("orderInfo")
	public Map<String, Object> orderInfo(@RequestParam(name = "orderCode")String orderCode) {
		Map<String, Object> map = new HashMap<>();
		
		List<OrderInfoDTO> orderInfo = service.getOrderInfo(orderCode);
		
		//주문번호
		String productOrderCode = orderInfo.get(0).getProductOrderCode();
		
		//제품 사이즈, 수량 담을 리스트
		List<Map<String, Object>> productDetails = new ArrayList<Map<String,Object>>();
		
		//제품 주문정보 map으로 가져오고 list에 넣기
		for(int i=0; i<orderInfo.size(); i++) {
			Map<String, Object> eachProduct = service.findByProductCode(orderInfo.get(i).getProductCode(), productOrderCode);
			productDetails.add(eachProduct);
		}
		map.put("orderInfo", orderInfo);
		map.put("productDetails", productDetails);
		return map;
	}
	
	// 제품코드 기반 옵션 리스트
	@GetMapping("productCodeList")
	public Map<String, Object> findByProductCode(@RequestParam(name= "productCode")String productCode,
			@RequestParam(name= "orderCode")String orderCode
			){
		return service.findByProductCode(productCode, orderCode);
	}
}
