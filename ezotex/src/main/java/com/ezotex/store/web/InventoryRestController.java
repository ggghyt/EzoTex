package com.ezotex.store.web;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.comm.GridUtil;
import com.ezotex.store.service.InventoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RequestMapping("/store/*")
@RestController
public class InventoryRestController {

	private final InventoryService service;
	
	@GetMapping("test2")
	public Map<String, Object> list(@RequestParam(name = "perPage", defaultValue = "1", required = false) int perPage
			) throws JsonMappingException, JsonProcessingException {

		// 만들어야됨
//		paging.setPageUnit(perPage);
//
//		// 페이징 조건
//		searchDTO.setStart(paging.getFirst());
//		searchDTO.setEnd(paging.getLast());
//
//		// 페이징처리
//		paging.setTotalRecord(service.getCount(searchDTO));
		
		
		// 페이징처리 만들어야됨
//		paging.getPage());
//		service.getCount(searchDTO));
		return GridUtil.grid(1, 100, service.DeliveryList());
		
	}

	
}
