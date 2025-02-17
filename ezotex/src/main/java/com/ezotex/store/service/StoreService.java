package com.ezotex.store.service;

import java.util.List;
import java.util.Map;

import com.ezotex.store.dto.StoreDeliveryDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;
import com.ezotex.store.dto.InventoryDTO;
import com.ezotex.store.dto.SizeDTO;

public interface StoreService {

	// 테스트
	public List<InventoryDTO> list();
	
	// 입고 예정 리스트
	public List<StoreDeliveryDTO> DeliveryList();
	
	// 납품리스트 기반 입고 제품 상세 조회
	public List<StoreDeliveryDetailsDTO> findByDeliveryCode(String DeliveryCode);
	
	// 제품코드 기반 옵션 리스트
	public Map<String, Object> findByProductCode(String productCode);
	
	// 제품 입고 등록
	public boolean InsertProduct(List<SizeDTO> list); 
	
}
