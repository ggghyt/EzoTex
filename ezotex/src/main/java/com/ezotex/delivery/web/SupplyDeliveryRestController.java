package com.ezotex.delivery.web;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.comm.GridUtil;
import com.ezotex.comm.dto.PagingDTO;
import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.OrderInsertDTO;
import com.ezotex.delivery.service.SupplyDeliveryService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/delivery/*")
public class SupplyDeliveryRestController {
	

	//납품 관리
	private SupplyDeliveryService service;
	
	//발주 조회
	@GetMapping("mtrilDelivery")
	public Map<String, Object> findAll(@RequestParam(name = "perPage", defaultValue = "1", required = false) int perPage,
									             @RequestParam(name = "page", defaultValue = "1")int page,
												 DeliveryRegistSearchDTO searchDTO) {
		
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

}
