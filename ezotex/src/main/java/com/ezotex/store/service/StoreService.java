package com.ezotex.store.service;

import java.util.List;
import java.util.Map;

import com.ezotex.comm.dto.PagingDTO;
import com.ezotex.store.dto.SizeDTO;
import com.ezotex.store.dto.StoreDeliveryDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;

public interface StoreService {

	// 페이지 총 수
	public int getCount();
	
	// 입고 예정 리스트
	public List<StoreDeliveryDTO> DeliveryList(PagingDTO paging);
	
	// 납품리스트 기반 입고 제품 상세 조회
	public List<StoreDeliveryDetailsDTO> findByDeliveryCode(String DeliveryCode);
	
	// 제품코드 기반 옵션 리스트
	public Map<String, Object> findByProductCode(String productCode);
	
	// 제품 입고 등록
	public boolean InsertProduct(List<SizeDTO> list); 
	
}
