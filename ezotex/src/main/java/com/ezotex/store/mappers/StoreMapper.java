package com.ezotex.store.mappers;

import java.util.List;


import org.apache.ibatis.annotations.Param;

import com.ezotex.store.dto.InventoryDTO;
import com.ezotex.store.dto.SizeDTO;
import com.ezotex.store.dto.StoreDeliveryDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;

public interface StoreMapper {
	
	// 테스트
	public List<InventoryDTO> list();
	
	// 입고 예정 리스트
	public List<StoreDeliveryDTO> DeliveryList();
	
	// 납품리스트 기반 입고 제품 상세 조회
	public List<StoreDeliveryDetailsDTO> findByDeliveryCode(String DeliveryCode);
	
	// 제품별 사이즈 리스트
    public List<StoreDeliveryDetailsDTO> findBySize(String productCode);
	
	// 제품코드 기반 옵션 리스트
	public List<StoreDeliveryDetailsDTO> findByProductCode(@Param("productCode")String productCode, @Param("list")List<StoreDeliveryDetailsDTO> list);
	
	// 제품 입고 등록
	public int InsertProduct(SizeDTO SDto); 
	
	// 제품 입고별 입고해야될 수량 수정
	public int UpdateDeliveryDtails(StoreDeliveryDetailsDTO detailDto);
}
