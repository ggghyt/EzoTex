package com.ezotex.delivery.web;

import java.sql.Date;
import java.util.List;
import java.util.Map;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.comm.GridUtil;
import com.ezotex.delivery.dto.Paging;
import com.ezotex.delivery.dto.DeliveryOrderListSearchDTO;
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
			                        DeliveryOrderListSearchDTO searchDTO
			                        ) throws JsonMappingException, JsonProcessingException {

		Paging paging = new Paging();
		
		paging.setPageUnit(perPage);	//페이지당 최대 건수
		paging.setPage(page);			//현재 페이지

		// 페이징 조건
		searchDTO.setStart(paging.getFirst());	//시작
		searchDTO.setEnd(paging.getLast());		//끝번호
		
		paging.setTotalRecord(service.getCount(searchDTO));

		return GridUtil.grid(paging.getPage(), service.getCount(searchDTO), service.getList(searchDTO));
		
	}
	
	@GetMapping("orderInfo")
	public List<OrderInfoDTO> orderInfo(@RequestParam(name = "orderCode")String orderCode) {
		return service.getOrderInfo(orderCode);
	}

}
