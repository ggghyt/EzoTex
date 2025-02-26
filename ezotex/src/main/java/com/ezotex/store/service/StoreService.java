package com.ezotex.store.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezotex.comm.dto.PagingDTO;
import com.ezotex.store.dto.DeliverySearchDTO;
import com.ezotex.store.dto.InventoryDTO;
import com.ezotex.store.dto.ProductInfoListDTO;
import com.ezotex.store.dto.ProductInfoSearchDTO;
import com.ezotex.store.dto.SizeDTO;
import com.ezotex.store.dto.StoreDeliveryDTO;
import com.ezotex.store.dto.StoreDeliveryDetailsDTO;

public interface StoreService {

	
	// 제품 목록 리스트
	public List<ProductInfoListDTO> productInfoList(ProductInfoSearchDTO searchDTO);
	
	// 제품 목록 total
	public int productInfoTotal(ProductInfoSearchDTO searchDTO);
	
	// 제품 컬러
	public List<ProductInfoListDTO> productColor(String productColor);
	
	// 제품 사이즈
	public List<ProductInfoListDTO> productSize(String productCode ,String productColor);
	
	
	
	
	
	
	/**
	 * =========================================== 반품으로 변경해야 되는 것들 =========================================== 
	 * */
	
	// 페이지 총 수(제품)
	public int getCount(DeliverySearchDTO searchDTO);
	
	// 페이지 총 수(자재)
	public int getMtCount(DeliverySearchDTO searchDTO);
	
	// 납품리스트별 총 제품 수량
	//public StoreDeliveryDTO deliveryQy();
	
	// 입고 예정 리스트(제품)
	public List<StoreDeliveryDTO> DeliveryList(DeliverySearchDTO searchDTO);
	
	// 입고 예정 리스트(자재)
	public List<StoreDeliveryDTO> MtDeliveryList(DeliverySearchDTO searchDTO);
	
	// 납품리스트 기반 입고 제품 상세 조회
	public List<StoreDeliveryDetailsDTO> findByDeliveryCode(String DeliveryCode);
	
	// 납품리스트 기반 입고 자재 상세 조회
	public List<StoreDeliveryDetailsDTO> findByMtDeliveryCode(String DeliveryCode);
	
	// 제품코드 기반 옵션 리스트
	public Map<String, Object> findByProductCode(String productCode, String deliveryCode);
	
	// 제품 입고 등록
	public boolean InsertProduct(List<SizeDTO> list); 
	
	// 자재 입고 등록
	public boolean MtInsertProduct(List<StoreDeliveryDetailsDTO> list);
	
	/**
	 * =========================================== 반품으로 변경해야 되는 것들 =========================================== 
	 * */
	
}
