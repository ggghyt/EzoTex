package com.ezotex.store.mappers;

import java.util.List;

import com.ezotex.store.dto.InventoryDTO;
import com.ezotex.store.dto.StoreDeliveryDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;

public interface InventoryMapper {
	
	// 테스트
	public List<InventoryDTO> list();
	
	// 입고 예정 리스트
	public List<StoreDeliveryDTO> DeliveryList();
	
	// 납품리스트 기반 입고 제품 상세 조회
	public List<StoreDeliveryDetailsDTO> findByDeliveryCode(String DeliveryCode);
	
}
