package com.ezotex.store.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezotex.comm.dto.PagingDTO;
import com.ezotex.store.dto.SizeDTO;
import com.ezotex.store.dto.StoreDeliveryDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;

public interface StoreMapper {
	
	// 페이지 총 수
	public int getCount();
	
	// 입고 예정 리스트
	public List<StoreDeliveryDTO> DeliveryList(PagingDTO paging);
	
	// 납품리스트별 총 제품 수량
	public StoreDeliveryDTO deliveryQy();
	
	// 납품리스트 기반 입고 제품 상세 조회
	public List<StoreDeliveryDetailsDTO> findByDeliveryCode(String DeliveryCode);
	
	// 제품별 사이즈 리스트
    public List<StoreDeliveryDetailsDTO> findBySize(String productCode);
	
	// 제품코드 기반 옵션 리스트
	public List<StoreDeliveryDetailsDTO> findByProductCode(@Param("productCode")String productCode, @Param("list")List<StoreDeliveryDetailsDTO> list);
	
	// 제품 입고 등록
	public int InsertProduct(@Param("list")SizeDTO SDto, @Param("name")String name); 
	
	// 제품 입고별 입고해야될 수량 수정
	public int UpdateDeliveryDtails(SizeDTO sizedto);
}
