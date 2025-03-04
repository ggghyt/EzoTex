package com.ezotex.delivery.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.comm.GridUtil;
import com.ezotex.comm.dto.PagingDTO;
import com.ezotex.delivery.dto.DeliveryProductInfo;
import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.DeliveryScheduleDTO;
import com.ezotex.delivery.dto.OrderInfoDTO;
import com.ezotex.delivery.dto.OrderInsertDTO;
import com.ezotex.delivery.dto.OrderProductDeliveryDTO;
import com.ezotex.delivery.dto.PackingDTO;
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
		PagingDTO paging = new PagingDTO();
		
		paging.setPageUnit(perPage);	//페이지당 최대 건수
		paging.setPage(page);			//현재 페이지

		// 페이징 조건
		searchDTO.setStart(paging.getFirst());	//시작
		searchDTO.setEnd(paging.getLast());		//끝번호
		
		paging.setTotalRecord(service.getCount(searchDTO));

		return GridUtil.grid(paging.getPage(), service.getCount(searchDTO), service.getList(searchDTO));
		
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
	
	//출고 등록
	@PostMapping("deliveryRegist")
	public Map<String, String> insertDelivery(@RequestBody List<OrderInsertDTO> insertData) {
		log.info(((Integer)insertData.size()).toString());
		Map<String, String> map = new HashMap<>();
		map.put("state", service.insertDelivery(insertData));
		//map.put("state", "fail");
		return map;
	}
	
	//출고 조회
	@GetMapping("getDeliveryList")
	public Map<String, Object> deliveryList(@RequestParam(name = "perPage", defaultValue = "1", required = false) int perPage,
											            @RequestParam(name = "page", defaultValue = "1")int page,
											            DeliveryRegistSearchDTO searchDTO) {
		
		log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		log.info(searchDTO.toString());
		PagingDTO paging = new PagingDTO();
		
		paging.setPageUnit(perPage);	//페이지당 최대 건수
		paging.setPage(page);			//현재 페이지

		// 페이징 조건
		searchDTO.setStart(paging.getFirst());	//시작
		searchDTO.setEnd(paging.getLast());		//끝번호
		
		paging.setTotalRecord(service.getCount(searchDTO));

		return GridUtil.grid(paging.getPage(), service.deliveryListCnt(searchDTO), service.deliveryList(searchDTO));
	}
	
	//포장목록 조회
	@GetMapping("getPackingList")
	public Map<String, Object> getPackingList(@RequestParam(name = "perPage", defaultValue = "1", required = false) int perPage,
											            @RequestParam(name = "page", defaultValue = "1")int page,
											            DeliveryRegistSearchDTO searchDTO) {
		
		log.info(searchDTO.toString());
		PagingDTO paging = new PagingDTO();
		
		paging.setPageUnit(perPage);	//페이지당 최대 건수
		paging.setPage(page);			//현재 페이지

		// 페이징 조건
		searchDTO.setStart(paging.getFirst());	//시작
		searchDTO.setEnd(paging.getLast());		//끝번호
		
		paging.setTotalRecord(service.packingListCnt(searchDTO));

		return GridUtil.grid(paging.getPage(), service.packingListCnt(searchDTO), service.packingList(searchDTO));
	}
	
	//출고건 담당자, 제품 리스트
	@GetMapping("deliveryInfo")
	public Map<String, Object> deliveryInfo(@RequestParam(name="deliveryCode")String deliveryCode) {
		log.info(deliveryCode);
		Map<String, Object> info = service.deliveryInfo(deliveryCode);
		return info;
	}
	
	//제품 리스트
	@GetMapping("deliveryProductList")
	public List<DeliveryProductInfo> deliveryProductList(@RequestParam(name="deliveryCode")String deliveryCode) { 
		return service.deliveryProductListWithLot(deliveryCode);
	}
	
	//출고 제품 상세
	@GetMapping("deliveryProductDetails")
	public Map<String, Object> deliveryDetails(@RequestParam(name="deliveryCode")String deliveryCode,
											   @RequestParam(name="productCode")String productCode) {
		log.info(productCode);
		log.info(deliveryCode);
		return service.deliveryProductDetails(deliveryCode, productCode);
	}
	
	//출고 제품 로트
	@GetMapping("deliveryLot")
	public List<OrderInsertDTO> deliveryLot(@Param("info")OrderInsertDTO info) {
		//deliveryCode, productCode, productSize, productColor
		return service.deliveryLot(info);
	}
	
	//출고 창고이름, 상자 사이즈 가져오기
	@GetMapping("packingSTDInfo")
	public Map<String, Object> deliveryLot() {
		return service.packingSTDInfo();
	}
	
	//포장등록
	@PostMapping("packingInsert")
	public String packingInsert(@RequestBody List<PackingDTO> info) {
		log.info(info.toString());
		//포장정보 등록
		service.insertPackingInfo(info);
		
		//배송스케줄 등록 --> 구현 안될것 같아 함께 넣고 트리거로 상태변경
		DeliveryScheduleDTO deSchedule = new DeliveryScheduleDTO();
		deSchedule.setDeliveryCode(info.get(0).getDeliveryCode());
		
		service.insertDivySchedule(deSchedule);
		return "success";
	}
}
