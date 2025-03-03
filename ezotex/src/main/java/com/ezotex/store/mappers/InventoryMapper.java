package com.ezotex.store.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezotex.store.dto.DeliverySearchDTO;
import com.ezotex.store.dto.ErrorProductDTO;
import com.ezotex.store.dto.InventoryDTO;
import com.ezotex.store.dto.SizeDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;
import com.ezotex.store.dto.storageInfoDTO;

public interface InventoryMapper {

	// 제품목록
	public List<InventoryDTO> productList(DeliverySearchDTO searchDTO);
	
	// 제품목록 카운트
	public int productListCount(DeliverySearchDTO searchDTO);
	
	// 제품별 옵션 리스트
	public List<Map<String, Object>> productInfoList(@Param("productCode")String productCode, @Param("list")List<StoreDeliveryDetailsDTO> list);
	
	// 제품 옵션별 LOT 리스트
	public List<InventoryDTO> inventoryList(@Param("productCode")String productCode, @Param("color")String color, @Param("sizeCode")String sizeCode);
	
	// 위치별 재고 상세 리스트(재고조정)
	public List<InventoryDTO> location(DeliverySearchDTO searchDTO);
	
	// 재고조정 LOT 카운터
	public int getCountProduct(DeliverySearchDTO searchDTO);
	
	// LOT별 불량처리 등록
	public int InsertErrorProduct(@Param("edto")ErrorProductDTO edto, @Param("name")String name);
	
	// 불량처리 후 재고량 업데이트
	public int InventoryUpdate(ErrorProductDTO edto);
	
	// 창고 위치 코드 리스트
	public List<storageInfoDTO> storageInfoList();
	
	// 재고조정 창고위치 이동
	public int locationUpdate(SizeDTO sdto);
	
}
