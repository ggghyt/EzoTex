package com.ezotex.delivery.web;

import java.sql.Date;
import java.util.Map;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.comm.GridUtil;
import com.ezotex.delivery.dto.Paging;
import com.ezotex.delivery.dto.DeliveryOrderListSearchDTO;
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
			                        @RequestParam(name = "page", defaultValue = "1")int page,
			                        DeliveryOrderListSearchDTO searchDTO
			                        ) throws JsonMappingException, JsonProcessingException {


		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+page);
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+searchDTO);
		Paging paging = new Paging();
		//DeliveryOrderListSearchDTO searchDTO = new DeliveryOrderListSearchDTO();
		
		/*
		  @RequestParam(name = "searchDeliveryCode", required = false)String searchDeliveryCode,
	        @RequestParam(name = "searchOrderCharger", required = false)String searchOrderCharger,
	        @RequestParam(name = "searchDeliveryCharger", required = false)String searchDeliveryCharger,
	        @RequestParam(name = "searchOrderDateStart", required = false)Date searchOrderDateStart,
	        @RequestParam(name = "searchOrderDateEnd", required = false)Date searchOrderDateEnd,
	        @RequestParam(name = "searchOrderDedtStart", required = false)Date searchOrderDedtStart,
	        @RequestParam(name = "searchOrderDedtEnd", required = false)Date searchOrderDedtEnd,
	        @RequestParam(name = "searchAmountStart", required = false)int searchAmountStart,
	        @RequestParam(name = "searchAmountEnd", required = false)int searchAmountEnd,
	        @RequestParam(name = "searchCompanyCode", required = false)String searchCompanyCode,
	        @RequestParam(name = "searchCompanyName", required = false)String searchCompanyName,
	        @RequestParam(name = "searchStatus", required = false)String searchStatus
		 */
//		searchDTO.setOrderCode(searchOrderCode);
//		searchDTO.setDeliveryCode(searchDeliveryCode);
//		searchDTO.setOrderCharger(searchOrderCharger);
//		searchDTO.setDeliveryCharger(searchDeliveryCharger);
//		searchDTO.setOrderDateStart(searchOrderDateStart);
//		searchDTO.setOrderDateEnd(searchOrderDateEnd);
//		searchDTO.setDedtStart(searchOrderDedtStart);
//		searchDTO.setDedtEnd(searchOrderDedtEnd);
//		searchDTO.setTotalAmountStart(searchAmountStart);
//		searchDTO.setTotalAmountEnd(searchAmountEnd);
//		searchDTO.setCompanyCode(searchCompanyCode);
//		searchDTO.setCompanyName(searchCompanyName);
//		searchDTO.setStatus(searchStatus);
		//위처럼 하드코딩 하지말고, DTO넣어서하면 한번에 들어감...
		


		
		// 만들어야됨
		paging.setPageUnit(perPage);	//페이지당 최대 건수
		paging.setPage(page);			//현재 페이지

		// 페이징 조건
		searchDTO.setStart(paging.getFirst());	//시작
		searchDTO.setEnd(paging.getLast());		//끝번호
		
		paging.setTotalRecord(service.getCount(searchDTO));

		// 페이징처리
		//paging.setTotalRecord(service.getCount());
		
		// 페이징처리 만들어야됨
		//paging.getPage();
		//service.getCount();
		//log.info(service.getList(searchDTO).toString());
		
		////////////////getCount랑 getList에 searchDTO넣기 
		//return null;
		return GridUtil.grid(paging.getPage(), service.getCount(searchDTO), service.getList(searchDTO));
		
	}

}
