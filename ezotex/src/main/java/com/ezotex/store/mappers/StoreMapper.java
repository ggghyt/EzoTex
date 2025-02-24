package com.ezotex.store.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezotex.store.dto.DeliverySearchDTO;
import com.ezotex.store.dto.InventoryDTO;
import com.ezotex.store.dto.SizeDTO;
import com.ezotex.store.dto.StoreDeliveryDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;

public interface StoreMapper {
	
	// 페이지 총 수(제품)
	public int getCount(DeliverySearchDTO searchDTO);
	
	// 페이지 총 수(자재)
	public int getMtCount(DeliverySearchDTO searchDTO);
	
	// 입고 예정 리스트(제품)
	public List<StoreDeliveryDTO> DeliveryList(DeliverySearchDTO searchDTO);
	
	// 입고 예정 리스트(자재)
	public List<StoreDeliveryDTO> MtDeliveryList(DeliverySearchDTO searchDTO);
	
	// 납품리스트별 총 제품 수량
	//public StoreDeliveryDTO deliveryQy();
	
	// 납품리스트 기반 입고 제품 상세 조회
	public List<StoreDeliveryDetailsDTO> findByDeliveryCode(String DeliveryCode);
	
	// 납품리스트 기반 입고 자재 상세 조회
	public List<StoreDeliveryDetailsDTO> findByMtDeliveryCode(String DeliveryCode);
	
	// 제품별 사이즈 리스트
    public List<StoreDeliveryDetailsDTO> findBySizeInventory(String productCode);
	
	// 제품코드 기반 옵션 리스트
	public List<Map<String, Object>> findByProductCode(@Param("productCode")String productCode, @Param("list")List<StoreDeliveryDetailsDTO> list, @Param("deliveryCode") String deliveryCode);
	
	// 제품 입고 등록
	public int InsertProduct(@Param("list")SizeDTO SDto, @Param("name")String name);
	
	// 자재 입고 등록
	public int MtInsertProduct(@Param("list")StoreDeliveryDetailsDTO sdto, @Param("name") String name);
	
	// 제품 입고별 입고해야될 수량 수정
	public int UpdateDeliveryDtails(SizeDTO sizedto);
}
