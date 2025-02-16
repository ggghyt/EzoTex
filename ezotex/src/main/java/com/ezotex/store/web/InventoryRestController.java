package com.ezotex.store.web;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.comm.GridData;
import com.ezotex.comm.GridUtil;
import com.ezotex.store.dto.SizeDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;
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
	
	@GetMapping("deliveryList")
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
	
	// 납품리스트 기반 입고 제품 상세 조회
	@GetMapping("deliveryDetailsList")
	public List<StoreDeliveryDetailsDTO> findByDelivertCode(@RequestParam(name= "deliveryCode") String deliveryCode){
		return service.findByDeliveryCode(deliveryCode);
	}

	// 제품코드 기반 옵션 리스트
	@GetMapping("productCodeList")
	public Map<String, Object> findByProductCode(@RequestParam(name= "productCode")String productCode){
		log.info(productCode);
		return service.findByProductCode(productCode);
	}
	
	@PostMapping("InsertTest")
	public String test(@RequestBody GridData<SizeDTO> sdata) {
		service.InsertProduct(sdata.getUpdatedRows());
		System.out.println("컨트롤러 : " + sdata);
		return "redirect:store/insertStore";
	}

	
}
