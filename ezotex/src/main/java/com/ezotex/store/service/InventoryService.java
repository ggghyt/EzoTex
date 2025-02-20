package com.ezotex.store.service;

import java.util.List;
import java.util.Map;

import com.ezotex.store.dto.ErrorProductDTO;
import com.ezotex.store.dto.InventoryDTO;

public interface InventoryService {

	// 제품목록
	public List<InventoryDTO> productList();
	
	// 제품별 옵션 리스트
	public Map<String, Object> productInfoList(String productCode);
	
	// 제품 옵션별 LOT 리스트
	public List<InventoryDTO> inventoryList(String productCode, String color, String sizeCode);
	
	// 위치별 재고 상세 리스트(재고조정)
	public List<InventoryDTO> location();
	
	// LOT별 불량처리 등록
	public boolean InsertErrorProduct(ErrorProductDTO edto);
	
	
	
}
