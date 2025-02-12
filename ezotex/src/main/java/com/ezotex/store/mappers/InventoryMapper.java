package com.ezotex.store.mappers;

import java.util.List;

import com.ezotex.store.dto.StoreDeliveryDTO;
import com.ezotex.store.dto.InventoryDTO;

public interface InventoryMapper {
	
	// 테스트
	public List<InventoryDTO> list();
	
	// 입고 예정 리스트
	public List<StoreDeliveryDTO> DeliveryList();
	
	// 입고 예정 제품 종류 수
	public List<StoreDeliveryDTO> DeliveryQy();
	
}
