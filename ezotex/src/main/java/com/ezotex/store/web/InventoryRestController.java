package com.ezotex.store.web;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezotex.comm.GridUtil;
import com.ezotex.comm.dto.PagingDTO;
import com.ezotex.store.dto.DeliverySearchDTO;
import com.ezotex.store.dto.ErrorProductDTO;
import com.ezotex.store.dto.InventoryDTO;
import com.ezotex.store.dto.ProductInfoListDTO;
import com.ezotex.store.dto.ProductInfoSearchDTO;
import com.ezotex.store.dto.SizeDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;
import com.ezotex.store.dto.storageInfoDTO;
import com.ezotex.store.service.InventoryService;
import com.ezotex.store.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RequestMapping("/store/*")
@RestController
public class InventoryRestController {

	private final StoreService service;
	private final InventoryService iService;
	
	
	// 제품 목록 리스트
	@GetMapping("productInfoList")
	public Map<String, Object> productInfoList(@RequestParam(name = "perPage", defaultValue = "1", required = false) int perPage,
									@RequestParam(name = "page", defaultValue = "1") int page,
									ProductInfoSearchDTO searchDTO
			) throws JsonMappingException, JsonProcessingException {

		PagingDTO paging = new PagingDTO();
		
		paging.setPageUnit(perPage);	// toast 페이지당 최대 건수
		paging.setPage(page);			// 현재 페이지

//		// 페이징 조건
		searchDTO.setStart(paging.getFirst());
		searchDTO.setEnd(paging.getLast());
//
//		// 페이징처리
		paging.setTotalRecord(service.productInfoTotal(searchDTO));
		
		//service.deliveryQy();
		// 페이징처리 만들어야됨
//		paging.getPage());
//		service.getCount(searchDTO));
		return GridUtil.grid(paging.getPage(), service.productInfoTotal(searchDTO), service.productInfoList(searchDTO));
	}
	
	
	// 제품별 색상
	@GetMapping("productColor")
	public List<ProductInfoListDTO> productColor(@RequestParam(name= "productCode") String productCode){
		return service.productColor(productCode);
	}
	
	
	
	
	
	
	 /**
	  * =========================================== 반품으로 변경해야 되는 것들 =========================================== 
	 **/
	
	
	// 입고 예정 리스트(제품)
	@GetMapping("deliveryList")
	public Map<String, Object> list(@RequestParam(name = "perPage", defaultValue = "1", required = false) int perPage,
									@RequestParam(name = "page", defaultValue = "1") int page,
									DeliverySearchDTO searchDTO
			) throws JsonMappingException, JsonProcessingException {

		PagingDTO paging = new PagingDTO();
		
		paging.setPageUnit(perPage);	// toast 페이지당 최대 건수
		paging.setPage(page);			// 현재 페이지

//		// 페이징 조건
		searchDTO.setStart(paging.getFirst());
		searchDTO.setEnd(paging.getLast());
//
//		// 페이징처리
		paging.setTotalRecord(service.getCount(searchDTO));
		
		//service.deliveryQy();
		// 페이징처리 만들어야됨
//		paging.getPage());
//		service.getCount(searchDTO));
		return GridUtil.grid(paging.getPage(), service.getCount(searchDTO), service.DeliveryList(searchDTO));
	}
	
	
	// 입고 예정 리스트(자재)
	@GetMapping("mtDeliveryList")
	public Map<String, Object> Mtlist(@RequestParam(name = "perPage", defaultValue = "1", required = false) int perPage,
									@RequestParam(name = "page", defaultValue = "1") int page,
									DeliverySearchDTO searchDTO
			) throws JsonMappingException, JsonProcessingException {

		PagingDTO paging = new PagingDTO();
		
		paging.setPageUnit(perPage);	// toast 페이지당 최대 건수
		paging.setPage(page);			// 현재 페이지

//			// 페이징 조건
		searchDTO.setStart(paging.getFirst());
		searchDTO.setEnd(paging.getLast());
//
//			// 페이징처리
		paging.setTotalRecord(service.getMtCount(searchDTO));
		
		//service.deliveryQy();
		// 페이징처리 만들어야됨
//			paging.getPage());
//			service.getCount(searchDTO));
		return GridUtil.grid(paging.getPage(), service.getCount(searchDTO), service.MtDeliveryList(searchDTO));
	}
	
	
	// 납품리스트 기반 입고 제품 상세 조회
	@GetMapping("deliveryDetailsList")
	public List<StoreDeliveryDetailsDTO> findByDelivertCode(@RequestParam(name= "deliveryCode") String deliveryCode){
		return service.findByDeliveryCode(deliveryCode);
	}
	
	// 납품리스트 기반 입고 자재 상세 조회
	@GetMapping("mtDeliveryDetailsList")
	public List<StoreDeliveryDetailsDTO> findByMtDelivertCode(@RequestParam(name= "deliveryCode") String deliveryCode){
		return service.findByMtDeliveryCode(deliveryCode);
	}
	

	// 제품코드 기반 옵션 리스트
	@GetMapping("productCodeList")
	public Map<String, Object> findByProductCode(@RequestParam(name= "productCode")String productCode,
			                                     @RequestParam(name= "deliveryCode")String deliveryCode){
		return service.findByProductCode(productCode,deliveryCode);
	}
	
	// 제품 옵션별 등록
	@PostMapping("InsertTest")
	public boolean test(@RequestBody List<SizeDTO> sdata) {
		return service.InsertProduct(sdata);
	}
	
	// 제품별 리스트
	@GetMapping("productQyList")
	public Map<String, Object> productList(@RequestParam(name = "perPage", defaultValue = "1", required = false) int perPage
			) throws JsonMappingException, JsonProcessingException {
		return GridUtil.grid(1, 100, iService.productList());
	}
	
	// 제품별 옵션 리스트
	@GetMapping("productListInfo")
	public Map<String, Object> productInfoList(@RequestParam(name= "productCode") String productCode){
		return iService.productInfoList(productCode);
	}
	
	/*
	 * // 제품별 옵션 리스트(수정후 불러오기)
	 * 
	 * @GetMapping("productListInfo") public List<InventoryDTO>
	 * productInfoUpdateList(@RequestParam(name= "productCode") String productCode){
	 * return iService.productInfoList(productCode); }
	 */
	
	// 재고조회 제품별 수량 LOT리스트
	@GetMapping("inventoryList")
	public List<InventoryDTO> inventoryList(@RequestParam(name= "productCode") String productCode,
								@RequestParam(name= "color") String color,
								@RequestParam(name= "sizeCode") String sizeCode) {
		return iService.inventoryList(productCode, color, sizeCode);
	}
	
	// 위치별 재고 상세 리스트(재고조정)
	@GetMapping("Management")
	public Map<String, Object> loction(@RequestParam(name = "perPage", defaultValue = "1", required = false) int perPage
			) throws JsonMappingException, JsonProcessingException {
		return GridUtil.grid(1, 100, iService.location());
	}
	
	// LOT별 불량처리 등록
	@PostMapping("InsertProductError")
	public boolean InsertProductError(@RequestBody ErrorProductDTO edto) {
		return iService.InsertErrorProduct(edto);
	}
	
	
	// 창고 위치코드 리스트
	@GetMapping("storageInfoList")
	public List<storageInfoDTO> storageInfoList(){
		return iService.storageInfoList();
	}
	
	// 자재 등록
	@PostMapping("productMtInsert")
	public boolean productMtInsert(@RequestBody List<StoreDeliveryDetailsDTO> idto){
		return service.MtInsertProduct(idto);
	}


	 /**
	  * =========================================== 반품으로 변경해야 되는 것들 =========================================== 
	 **/
	
	
}
