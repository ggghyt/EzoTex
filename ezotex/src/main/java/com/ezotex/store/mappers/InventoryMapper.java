package com.ezotex.store.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezotex.store.dto.InventoryDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;

public interface InventoryMapper {

	// 제품목록
	public List<InventoryDTO> productList();
	
	// 제품별 옵션 리스트
	public List<StoreDeliveryDetailsDTO> productInfoList(@Param("productCode")String productCode, @Param("list")List<StoreDeliveryDetailsDTO> list);
	
	// 제품 옵션별 LOT 리스트
	public List<InventoryDTO> inventoryList(@Param("productCode")String productCode, @Param("color")String color, @Param("sizeCode")String sizeCode);
	
}
