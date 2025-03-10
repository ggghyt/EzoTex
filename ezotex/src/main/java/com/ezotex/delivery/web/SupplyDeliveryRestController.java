package com.ezotex.delivery.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.comm.GridUtil;
import com.ezotex.comm.dto.PagingDTO;
import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.OrderInfoDTO;
import com.ezotex.delivery.dto.OrderInsertDTO;
import com.ezotex.delivery.service.SupplyDeliveryService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/delivery/*")
public class SupplyDeliveryRestController {

	//납품 관리
	private SupplyDeliveryService service;
	
	@Autowired
	 private HttpSession session;
	
	
	//발주 조회
	@GetMapping("mtrilDelivery")
	public Map<String, Object> findAll(@RequestParam(name = "perPage", defaultValue = "1", required = false) int perPage,
									             @RequestParam(name = "page", defaultValue = "1")int page,
												 DeliveryRegistSearchDTO searchDTO) {
		
		String targetComCode = (String) session.getAttribute("code");
		
		searchDTO.setTargetCompany(targetComCode);
	
		
		PagingDTO paging = new PagingDTO();
		
		paging.setPageUnit(perPage);	//페이지당 최대 건수
		paging.setPage(page);			//현재 페이지

		// 페이징 조건
		searchDTO.setStart(paging.getFirst());	//시작
		searchDTO.setEnd(paging.getLast());		//끝번호
		
		paging.setTotalRecord(service.getCount(searchDTO));

		return GridUtil.grid(paging.getPage(), service.getCount(searchDTO), service.getList(searchDTO));
	}
	
	@GetMapping("mtrilOrderInfo")
	public List<OrderInsertDTO> orderInfo(@RequestParam(name = "orderCode")String orderCode) {
		log.info(orderCode);
		return service.orderInfo(orderCode);
	}
	
	@PostMapping("supplyDeliveryRegist")
	public Map<String, String> insertDelivery(@RequestBody List<OrderInsertDTO> insertData) {
		log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		log.info(insertData.toString());
		Map<String, String> map = new HashMap<>();
		map.put("state", service.insertDelivery(insertData));
		return map; 
	}
	
	//출고 조회
	@GetMapping("mtrilDeliveryList")
	public Map<String, Object> mtrilDeliveryList(@RequestParam(name = "perPage", defaultValue = "1", required = false) int perPage,
									             @RequestParam(name = "page", defaultValue = "1")int page,
												 DeliveryRegistSearchDTO searchDTO) {
		String targetComCode = (String) session.getAttribute("code");
		
		searchDTO.setTargetCompany(targetComCode);
	
		
		PagingDTO paging = new PagingDTO();
		
		paging.setPageUnit(perPage);	//페이지당 최대 건수
		paging.setPage(page);			//현재 페이지

		// 페이징 조건
		searchDTO.setStart(paging.getFirst());	//시작
		searchDTO.setEnd(paging.getLast());		//끝번호
		
		paging.setTotalRecord(service.supplyDeliveryListCount(searchDTO));

		return GridUtil.grid(paging.getPage(), paging.getTotalRecord(), service.supplyDeliveryList(searchDTO));
	}
	
	//출고 단건 조회
	@GetMapping("mtrilOrderList")
	public List<OrderInfoDTO> deliveryInfo(@RequestParam(name = "deliveryCode")String deliveryCode) {
		return service.deliveryInfo(deliveryCode);
	}
	
}
