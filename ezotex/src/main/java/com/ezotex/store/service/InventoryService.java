package com.ezotex.store.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezotex.store.dto.InventoryDTO;

public interface InventoryService {

	// 제품목록
	public List<InventoryDTO> productList();
	
	// 제품별 옵션 리스트
	public Map<String, Object> productInfoList(String productCode);
	
	// 제품 옵션별 LOT 리스트
	public List<InventoryDTO> inventoryList(String productCode, String color, String sizeCode);
	
}
