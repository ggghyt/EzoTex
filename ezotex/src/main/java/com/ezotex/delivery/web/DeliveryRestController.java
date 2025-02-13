package com.ezotex.delivery.web;

import java.util.Map;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.comm.GridUtil;
import com.ezotex.delivery.dto.Paging;
import com.ezotex.delivery.dto.SearchDTO;
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
	public Map<String, Object> list(@RequestParam(name = "perPage", defaultValue = "1", required = false) int perPage,
			                        @RequestParam(name = "page", defaultValue = "1")int page) throws JsonMappingException, JsonProcessingException {
		
		
		Paging paging = new Paging();
		SearchDTO searchDTO = new SearchDTO();
		
		// 만들어야됨
		paging.setPageUnit(perPage);	//페이지당 최대 건수
		paging.setPage(page);			//현재 페이지

		// 페이징 조건
		searchDTO.setStart(paging.getFirst());
		searchDTO.setEnd(paging.getLast());
		
		paging.setTotalRecord(service.getCount());

		// 페이징처리
		//paging.setTotalRecord(service.getCount());
		
		
		// 페이징처리 만들어야됨
		//paging.getPage();
		//service.getCount();
		
		return GridUtil.grid(paging.getPage(), service.getCount(), service.getList(searchDTO));
		
	}
	// 만들어야됨
//	paging.setPageUnit(perPage);
//
//	// 페이징 조건
//	searchDTO.setStart(paging.getFirst());
//	searchDTO.setEnd(paging.getLast());
//
//	// 페이징처리
//	paging.setTotalRecord(service.getCount(searchDTO));
	
	
	// 페이징처리 만들어야됨
//	paging.getPage());
//	service.getCount(searchDTO));
//	return GridUtil.grid(1, 100, service.DeliveryList());
	
}
